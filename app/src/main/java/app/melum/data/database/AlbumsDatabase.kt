package app.melum.data.database

import androidx.annotation.WorkerThread
import app.melum.data.database.pojo.RealmAlbum
import app.melum.entities.Album
import io.realm.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AlbumsDatabase(val config: RealmConfiguration) {

    fun <T> Realm.with(func: (Realm) -> T?): T? =
        use {
            func(this)
        }

    fun Realm.inTransaction(func: (Realm) -> Unit) {
        use {
            beginTransaction()
            func(this)
            commitTransaction()
        }
    }

    private suspend fun <T : RealmObject, S : RealmQuery<T>> findAllAwait(query: S): RealmResults<T> =
        suspendCancellableCoroutine { continuation ->
            val listener = RealmChangeListener<RealmResults<T>> { t -> continuation.resume(t) }
            query.findAllAsync().addChangeListener(listener)
        }

    private suspend fun <S : RealmObject> RealmQuery<S>.await() = findAllAwait(this)

    @WorkerThread
    suspend fun getSavedAlbums(): List<Album> {
        return Realm.getInstance(config)
            .with { realm ->
                val realmQuery = realm.where(RealmAlbum::class.java)
                realmQuery.findAll()?.let { realm.copyFromRealm(it) }?.map { it.toAlbum() }
            } ?: listOf()
    }

    @WorkerThread
    suspend fun saveAlbum(album: Album) {
        Realm.getInstance(config)
            .inTransaction {
                it.insertOrUpdate(RealmAlbum.fromAlbum(album))
            }
    }

    @WorkerThread
    suspend fun deleteSavedAlbum(id: String) {
        Realm.getInstance(config)
            .inTransaction {
                it.where(RealmAlbum::class.java)
                    .equalTo("id", id)
                    .findFirst()
                    ?.deleteFromRealm()
            }
    }


}