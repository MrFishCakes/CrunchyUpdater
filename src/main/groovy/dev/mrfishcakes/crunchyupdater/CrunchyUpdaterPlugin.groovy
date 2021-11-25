package dev.mrfishcakes.crunchyupdater

import org.gradle.api.Plugin
import org.gradle.api.Project

class CrunchyUpdaterPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        CrunchyUpdaterConfig config = project.extensions.create('crunchUpdater', CrunchyUpdaterConfig)

        project.task('upstreamUpdates') {
            CrunchyUpdater updater = new CrunchyUpdater(config.getRepoOwner().get(), config.getRepoName().get(), config.getOldHash().get(), config.getNewHash().get())

            updater.fetchCommitChanges().whenComplete((results, ex) -> {
                if (ex != null) {
                    ex.printStackTrace()
                    return
                }

                if (results.empty) {
                    println('No new updates found!')
                    return
                }

                println(config.getHeader())
                results.forEach(result -> println(result))
            })
        }
    }

}
