package dev.mrfishcakes.crunchyupdater;

import org.gradle.internal.impldep.com.google.gson.JsonElement;
import org.gradle.internal.impldep.com.google.gson.JsonObject;
import org.gradle.internal.impldep.com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CrunchyUpdater {

    private static final String COMPARE_URL = "https://api.github.com/repos/%s/%s/compare/%s...%s";

    private final String owner, repo, oldTag, newTag;

    /**
     * Create a new instance
     *
     * @param owner  Repository owner
     * @param repo   Repository name
     * @param oldTag Old commit tag
     * @param newTag New commit tag
     */
    public CrunchyUpdater(String owner, String repo, String oldTag, String newTag) {
        this.owner = Objects.requireNonNull(owner, "Owner cannot be null");
        this.repo = Objects.requireNonNull(repo, "Repo cannot be null");
        this.oldTag = Objects.requireNonNull(oldTag, "Old tag cannot be null");
        this.newTag = Objects.requireNonNull(newTag, "New tag cannot be null");
    }

    /**
     * Fetch all commit changes between the old and new hash.
     *
     * @return {@link CompletableFuture} with a {@link Set} containing all commits.
     * If the future fails, then a null set will be returned
     */
    public CompletableFuture<Set<String>> fetchCommitChanges() {
        return CompletableFuture.supplyAsync(() -> {
            final Set<String> results = new HashSet<>();

            try {
                URL url = new URL(String.format(COMPARE_URL, owner, repo, oldTag, newTag));
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    JsonElement element = JsonParser.parseReader(reader);
                    JsonElement commits = element.getAsJsonObject().get("commits");

                    for (JsonElement commit : commits.getAsJsonArray()) {
                        final JsonObject baseObject = commit.getAsJsonObject();
                        final JsonObject commitObject = baseObject.getAsJsonObject("commit");
                        final String message = commitObject.get("message").getAsString();

                        results.add(baseObject.get("html_url").getAsString() + " " + message.substring(0, message.lastIndexOf("(") - 1));
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();

                return null;
            }

            return results;
        });

    }

}
