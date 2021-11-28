package dev.mrfishcakes.crunchyupdater

import org.gradle.api.provider.Property

/**
 * Interface for defining values within configuration block
 *
 * @author MrFishCakes
 */
interface CrunchyUpdaterConfig {

    /**
     * Header to be printed before commits
     *
     * @return Header message
     */
    Property<String> getHeader()

    /**
     * GitHub account that owns the repository
     *
     * @return Repository owner
     */
    Property<String> getRepoOwner()

    /**
     * Name of the repository
     *
     * @return Repository name
     */
    Property<String> getRepoName()

    /**
     * Old git commit hash to compare from
     *
     * @return Old git hash
     */
    Property<String> getOldHash()

    /**
     * New git commit hash to compare against
     *
     * @return New git hash
     */
    Property<String> getNewHash()

}