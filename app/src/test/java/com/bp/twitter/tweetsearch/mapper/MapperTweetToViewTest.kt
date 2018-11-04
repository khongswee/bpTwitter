package com.bp.twitter.tweetsearch.mapper

import com.bp.twitter.data.factory.SearchTweetResponseFactory
import com.bp.twitter.presentation.tweetlist.mapper.TweetDataMapToView
import com.bp.twitter.presentation.tweetlist.model.HashTagDisplatModel
import com.bp.twitter.presentation.tweetlist.model.MediaTypeDisplay
import com.bp.twitter.presentation.tweetlist.model.TweetDisplayModel
import com.bp.twitter.presentation.tweetlist.model.TweetMediaDisplay
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MapperTweetToViewTest {
    private lateinit var mapper: TweetDataMapToView

    @Before
    fun setUp() {
        mapper = TweetDataMapToView()
    }

    @Test
    fun tweet_content_text() {
        val data = stubTweetData(MediaTypeDisplay.TEXT)
        val expected = listOf<TweetDisplayModel>(
                TweetDisplayModel(1057155273456943104, "r[e]\uD83D\uDC7B\uD83E\uDD8B"
                        , "rerefye"
                        , "http://pbs.twimg.com/profile_images/1056377966375006210/FLKlNeHp_normal.jpg"
                        , false, "", emptyList()
                        , "Someone, help me with Korean language. I'm gonna spam his IG asking for BLACKPINK's full album"
                        , mapper.convertTimeGMT("Tue Oct 30 06:20:32 +0000 2018"), 0, 0
                        , TweetMediaDisplay(MediaTypeDisplay.TEXT, emptyList(), ""))
        )

        assertThat(data, `is`(expected))

    }

    @Test
    fun tweet_content_text_and_retweet() {
        val data = stubTweetData(MediaTypeDisplay.TEXT, true)
        val expected = listOf<TweetDisplayModel>(
                TweetDisplayModel(1058201319629037568, "ara♡'s felix"
                        , "spearkids"
                        , "http://pbs.twimg.com/profile_images/1058192186318163969/2h7eV7_S_normal.jpg"
                        , true, "\uD83C\uDF39Yes or yes, 1¹¹=1, LM5, JennieSolo", emptyList()
                        , """
                            quiero volver mi cuenta más multifandom así que si les gustan estos grupos den rt y les doy follow y nos hacemos mutuals uwu

                            →stray kids
                            →(G)i-dle
                            →blackpink
                            →twice
                            →seventeen
                            →bts
                            →hyuna
                            →nct
                            →izone
                            →got7
                        """.trimIndent()
                        , mapper.convertTimeGMT("Fri Nov 02 03:23:01 +0000 2018"), 18, 23
                        , TweetMediaDisplay(MediaTypeDisplay.TEXT, emptyList(), ""))
        )

        assertThat(data, `is`(expected))

    }

    @Test
    fun tweet_content_photo() {
        val data = stubTweetData(MediaTypeDisplay.PHOTO)
        val expected = listOf<TweetDisplayModel>(
                TweetDisplayModel(1056871585854844928, "챙 오마갓"
                        , "chaeng_ohmygash"
                        , "http://pbs.twimg.com/profile_images/1009059972812963840/PNI_7mtK_normal.jpg"
                        , false, "", listOf(HashTagDisplatModel("#blackpink", listOf(31, 41))
                        , HashTagDisplatModel("#lisa", listOf(42, 47))
                        , HashTagDisplatModel("#블랙핑크", listOf(48, 53))
                        , HashTagDisplatModel("#리사", listOf(54, 57)))
                        , "Lisa how's your trip so far? \n\n#blackpink #lisa #블랙핑크 #리사 https://t.co/zEQZqNGJ2U"
                        , mapper.convertTimeGMT("Mon Oct 29 11:33:16 +0000 2018"), 639, 1039
                        , TweetMediaDisplay(MediaTypeDisplay.PHOTO, listOf("http://pbs.twimg.com/media/DqrC_rFUwAEKcXl.jpg"
                        , "http://pbs.twimg.com/media/DqrC_rLU0AEMbq9.jpg"
                        , "http://pbs.twimg.com/media/DqrC_rIVAAEltTg.jpg"), ""))
        )

        assertThat(data, `is`(expected))

    }

    @Test
    fun tweet_content_photo_and_retweet() {
        val data = stubTweetData(MediaTypeDisplay.PHOTO, true)
        val expected = listOf<TweetDisplayModel>(
                TweetDisplayModel(1058194543768559600, "STOP SLEEPING ON WJSN"
                        , "takoyakiwinner"
                        , "http://pbs.twimg.com/profile_images/989012895508844544/nq4d2dVK_normal.jpg"
                        , true, "nad", emptyList()
                        , "YG:\nBlackpink : 1 mini\nIkon: 3 album\nWinner: 1 album \nBigbang : single\n\n🙃🙃🙃🙃🙃 https://t.co/zC4BZZ8YMI"
                        , mapper.convertTimeGMT("Wed Oct 31 07:14:53 +0000 2018"), 2107, 2194
                        , TweetMediaDisplay(MediaTypeDisplay.PHOTO, listOf("http://pbs.twimg.com/media/Dq0bJB6XcAAm51W.jpg"), ""))
        )


        assertThat(data, `is`(expected))

    }


    @Test
    fun tweet_content_video() {
        val data = stubTweetData(MediaTypeDisplay.VIDEO)
        val expected = listOf<TweetDisplayModel>(
                TweetDisplayModel(1057833063252553731, "LISANATIONS"
                        , "LISANATIONS_"
                        , "http://pbs.twimg.com/profile_images/975249607658676224/T_B8-sDj_normal.jpg"
                        , false, "", listOf(HashTagDisplatModel("#블랙핑크", listOf(106, 111))
                        , HashTagDisplatModel("#BLACKPINK", listOf(112, 122))
                        , HashTagDisplatModel("#리사", listOf(123, 126))
                        , HashTagDisplatModel("#LISA", listOf(127, 132))
                        , HashTagDisplatModel("#MOONSHOT", listOf(133, 142)))
                        , "[UPDATE] 181101 — Lisa's Moonshot video for China 11.11 shopping festival ✨ \n" +
                        "\n" +
                        "\uD83D\uDD17 https://t.co/ikSU52XA2w\n" +
                        "\n" +
                        "#블랙핑크 #BLACKPINK #리사 #LISA #MOONSHOT https://t.co/jKZZqgH0aE"
                        , mapper.convertTimeGMT("Thu Nov 01 03:13:50 +0000 2018"), 1003, 1332
                        , TweetMediaDisplay(MediaTypeDisplay.VIDEO, listOf("https://video.twimg.com/ext_tw_video/1057832994927370241/pu/vid/318x180/s3WfLzZ-2zV6T0ek.mp4?tag=5"
                        , "https://video.twimg.com/ext_tw_video/1057832994927370241/pu/vid/852x480/0Dh9me51-9ldZOPV.mp4?tag=5"
                        , "https://video.twimg.com/ext_tw_video/1057832994927370241/pu/pl/KiP9UQNNgjUG8vdC.m3u8?tag=5"
                        , "https://video.twimg.com/ext_tw_video/1057832994927370241/pu/vid/638x360/3NqYxdr0GEs62ysw.mp4?tag=5"), "http://pbs.twimg.com/ext_tw_video_thumb/1057832994927370241/pu/img/cV-Z34JiInSA_D-t.jpg"))
        )

        assertThat(data, `is`(expected))

    }

    @Test
    fun tweet_content_video_and_retweet() {
        val data = stubTweetData(MediaTypeDisplay.VIDEO, true)
        val expected = listOf<TweetDisplayModel>(
                TweetDisplayModel(1058607944126722049, "YG FAMILY"
                        , "ygent_official"
                        , "http://pbs.twimg.com/profile_images/869446110246428673/3vewgMjz_normal.jpg"
                        , true, "ตอบช้ามาก ขออภัย", listOf(HashTagDisplatModel("#JENNIE", listOf(0, 7))
                        , HashTagDisplatModel("#SOLO", listOf(9, 14))
                        , HashTagDisplatModel("#BLACKPINK", listOf(80, 90))
                        , HashTagDisplatModel("#블랙핑크", listOf(91, 96))
                        , HashTagDisplatModel("#제니", listOf(97, 100))
                        , HashTagDisplatModel("#YG", listOf(101, 104)))
                        , """
                            #JENNIE '#SOLO' TEASER VIDEO #2

                            First Single &amp; M/V Release
                            ✅ 2018. 11. 12

                            #BLACKPINK #블랙핑크 #제니 #YG https://t.co/KuOjaj1ycB
                        """.trimIndent()
                        , mapper.convertTimeGMT("Sat Nov 03 06:00:28 +0000 2018"), 7179, 11457
                        , TweetMediaDisplay(MediaTypeDisplay.VIDEO, listOf("https://video.twimg.com/amplify_video/1058503972003110912/vid/1280x720/Ocl3MLj0OXAFUY3D.mp4?tag=8"
                        , "https://video.twimg.com/amplify_video/1058503972003110912/pl/eW8PO4hCHmIcUHKy.m3u8?tag=8"
                        , "https://video.twimg.com/amplify_video/1058503972003110912/vid/640x360/040_wsaS5fP28V9N.mp4?tag=8"
                        , "https://video.twimg.com/amplify_video/1058503972003110912/vid/320x180/_1wyZfYaDBrhY8TZ.mp4?tag=8"), "http://pbs.twimg.com/media/DrDK14cUUAA4tZI.jpg"))
        )

        assertThat(data, `is`(expected))

    }

    @Test
    fun tweet_content_gif() {
        val data = stubTweetData(MediaTypeDisplay.GIF)
        val expected = listOf<TweetDisplayModel>(
                TweetDisplayModel(1057022911515639809, "BLACKPINK Charts"
                        , "chartsblackpink"
                        , "http://pbs.twimg.com/profile_images/1053821230669852674/zU9VwTiS_normal.jpg"
                        , false, "", emptyList()
                        , "BLACKPINK is the first K-Pop girl group to debut on Billboard Hot 100 twice. https://t.co/xo77AQ8inr"
                        , mapper.convertTimeGMT("Mon Oct 29 21:34:35 +0000 2018"), 1272, 3539
                        , TweetMediaDisplay(MediaTypeDisplay.GIF, listOf("https://video.twimg.com/tweet_video/DqtMs0KXQAAlspa.mp4"), "http://pbs.twimg.com/tweet_video_thumb/DqtMs0KXQAAlspa.jpg"))
        )

        assertThat(data, `is`(expected))

    }

    @Test
    fun tweet_content_gif_and_retweet() {
        val data = stubTweetData(MediaTypeDisplay.GIF, true)
        val expected = listOf<TweetDisplayModel>(
                TweetDisplayModel(1058617030356520961, "미스터리어스"
                        , "MYSTERIOUS_BP"
                        , "http://pbs.twimg.com/profile_images/1057873463304568832/i_x6p4Y2_normal.jpg"
                        , true, "JACKSONWearingCapIsTheBest", listOf(HashTagDisplatModel("#BLACKPINK", listOf(19, 29))
                        , HashTagDisplatModel("#ROSÉ", listOf(30, 35)))
                        , """
                            스타로드 움짤  // GIF 💘 #BLACKPINK #ROSÉ
                            오늘채영이 도 귀여움 열심히하는중...ㅠ^ㅠ❤️

                            🔗https://t.co/lgWV0l90t2 https://t.co/4yCTnDEc2I
                        """.trimIndent()
                        , mapper.convertTimeGMT("Fri Nov 02 13:45:03 +0000 2018"), 167, 285
                        , TweetMediaDisplay(MediaTypeDisplay.GIF, listOf("https://video.twimg.com/tweet_video/DrAGcnCV4AAXlm0.mp4"), "http://pbs.twimg.com/tweet_video_thumb/DrAGcnCV4AAXlm0.jpg"))
        )

        assertThat(data, `is`(expected))

    }

    private fun stubTweetData(type: MediaTypeDisplay, isRetweet: Boolean = false): List<TweetDisplayModel> {
        val tweetData = SearchTweetResponseFactory.makeSearchTweetResponseHasData(1, type, isRetweet)
        return tweetData.statuses.map { mapper.mapToView(it) }
    }


}