package com.example.paulo.events.Web_Service

import rx.Observable

/**
 * News Manager allows you to request more news from Reddit.
 *
 * @author juancho
 */
class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(limit: String = "10"): Observable<List<RedditNewsDataResponse>> {
        return Observable.create {
            subscriber ->
            val callResponse = api.getNews("", limit)
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val news = response.body().data.children.map {
                    val item = it.data
                    RedditNewsDataResponse(item.author, item.title, item.num_comments,
                            item.created, item.thumbnail, item.url)
                }
                subscriber.onNext(news)
                subscriber.onCompleted()

                println()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}