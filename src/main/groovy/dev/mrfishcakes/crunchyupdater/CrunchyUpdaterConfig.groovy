package dev.mrfishcakes.crunchyupdater

import org.gradle.api.provider.Property

interface CrunchyUpdaterConfig {

    Property<String> getHeader()
    Property<String> getRepoOwner()
    Property<String> getRepoName()
    Property<String> getOldHash()
    Property<String> getNewHash()

}