package com.students.salonapp.data.supabase

import com.students.salonapp.data.models.*
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseRepository(
    client: SupabaseClient = SupabaseClientInstance.client,
    private val useStub: Boolean = false // флаг для использования заглушек !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
) {

    private val rest = client.postgrest

    internal val stubServices = listOf(
        Service(
            id = "f3a7d9e2-c23b-4f0a-9d1e-42b7a9d564e4",
            name = "Lady-Dance",
            description = "Танцы для настоящих леди",
            duration = 90,
            price = 500.0,
            image_url = "ladydance",
            category_id = "2a5e8db6-97b9-4a3a-81d4-289ab8b0dc22"
        ),
        Service(
            id = "7e81b514-3f6a-4a0c-b4d8-938cfa0f2e3b",
            name = "High Hills",
            description = "Будь на высоте",
            duration = 60,
            price = 600.0,
            image_url = "bachata",
            category_id = "e08679a8-dd3f-4bb2-a019-a45c158dfac8"
        ),
        Service(
            id = "2a5e8db6-97b9-4a3a-81d4-289ab8b0dc22",
            name = "Бачата",
            description = "Страстный и чувственный латиноамериканский танец для всех уровней",
            price = 1000.0,
            duration = 130,
            image_url = "bachata",
            category_id = "2a5e8db6-97b9-4a3a-81d4-289ab8b0dc22"
        ),
        Service(
            id = "d4f5a6c1-8e72-4b39-9a4d-57c9b84f2e12",
            name = "Зумба",
            description = "Динамичная фитнес-программа с элементами латиноамериканских танцев и заводной музыкой",
            price = 900.0,
            duration = 90,
            image_url = "zumba",
            category_id = "e08679a8-dd3f-4bb2-a019-a45c158dfac8"
        ),
        Service(
            id = "d4e5f678-90ab-cdef-1234-567890abcdef",
            name = "Кизомба",
            description = "Плавный и чувственный афро-латинский танец для глубокого погружения в музыку и движение",
            price = 1000.0,
            duration = 45,
            image_url = "kizombi",
            category_id = "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
        ),
        Service(
            id = "e5f67890-abcd-ef12-3456-7890abcdef12",
            name = "Танго",
            description = "Элегантный и страстный аргентинский танец с глубокими эмоциями и выразительной хореографией",
            price = 1200.0,
            duration = 60,
            image_url = "",
            category_id = "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
        ),
        Service(
            id = "f67890ab-cdef-1234-5678-90abcdef1234",
            name = "Стриптиз",
            description = "Искусство соблазнительных движений и пластики тела для раскрепощения и уверенности",
            price = 2000.0,
            duration = 120,
            image_url = "",
            category_id = "b2c3d4e5-f678-90ab-cdef-1234567890ab"
        )
    )

    internal val stubMasters = listOf(
        Master(
            id = "aabbccdd-eeff-0011-2233-445566778899",
            name = "Максим Прозоров",
            rating = 5.0,
            bio = "Инструктор по мужскому стриптизу",
            image_url = "https://avatars.mds.yandex.net/i?id=d9eff7b7ffe7b72c8b11437c5aa95084_l-9222726-images-thumbs&n=13"
        ),
        Master(
            id = "bbccddee-ff00-1122-3344-556677889900",
            name = "Полунина Алиса",
            rating = 4.8,
            bio = "Инструктор по бачате",
            image_url = "https://avatars.mds.yandex.net/i?id=1cb860ee536b80c7e936ffbfd79f18c1_l-5150957-images-thumbs&n=13"
        )
    )

    internal val stubBookings = listOf(
        Booking(
            id = "cdef1234-5678-90ab-cdef-1234567890ab",
            user_id = "test_user",
            service_id = "d4e5f678-90ab-cdef-1234-567890abcdef",
            master_id = "aabbccdd-eeff-0011-2233-445566778899",
            date = "2025-06-03",
            time = "10:00",
            total_price = 1000.0
        ),
        Booking(
            id = "def12345-6789-0abc-def1-234567890abc",
            user_id = "test_user",
            service_id = "e5f67890-abcd-ef12-3456-7890abcdef12",
            master_id = "aabbccdd-eeff-0011-2233-445566778899",
            date = "2025-06-04",
            time = "12:00",
            total_price = 1500.0
        )
    )

    suspend fun getServiceById(serviceId: String): Service? =
        if (useStub) stubServices.find { it.id == serviceId }
        else try {
            withContext(Dispatchers.IO) {
                rest["services"].select {
                    filter { eq("id", serviceId) }
                }.decodeSingleOrNull<Service>()
            } ?: getServiceRoomRepoSafely()?.getAllServices()?.find { it.id == serviceId } ?: stubServices.find { it.id == serviceId }
        } catch (_: Exception) {
            getServiceRoomRepoSafely()?.getAllServices()?.find { it.id == serviceId } ?: stubServices.find { it.id == serviceId }
        }

    suspend fun getMasterById(masterId: String): Master? =
        if (useStub) stubMasters.find { it.id == masterId }
        else try {
            withContext(Dispatchers.IO) {
                rest["masters"].select {
                    filter { eq("id", masterId) }
                }.decodeSingleOrNull<Master>()
            } ?: getMasterRoomRepoSafely()?.getAllMasters()?.find { it.id == masterId } ?: stubMasters.find { it.id == masterId }
        } catch (_: Exception) {
            getMasterRoomRepoSafely()?.getAllMasters()?.find { it.id == masterId } ?: stubMasters.find { it.id == masterId }
        }

    suspend fun createBooking(booking: BookingCreateRequest): Booking? =
        if (useStub) Booking(
            id = (stubBookings.size + 1).toString(),
            user_id = booking.user_id,
            service_id = booking.service_id,
            master_id = booking.master_id,
            date = booking.date,
            time = booking.time,
            total_price = booking.total_price
        )
        else withContext(Dispatchers.IO) {
            rest["bookings"].insert(booking) { single() }.decodeSingleOrNull<Booking>()
        }

    suspend fun getUserBookings(userId: String): List<Booking> =
        if (useStub) stubBookings.filter { it.user_id == userId }
        else try {
            val result = withContext(Dispatchers.IO) {
                rest["bookings"].select {
                    filter { eq("user_id", userId) }
                    order("date", Order.DESCENDING)
                }.decodeList<Booking>()
            }
            when {
                result.isNotEmpty() -> result
                else -> {
                    // fallback на Room
                    try {
                        val roomRepo = getBookingRoomRepoSafely()
                        val local = roomRepo?.getUserBookings(userId)
                        if (!local.isNullOrEmpty()) local else emptyList()
                    } catch (_: Exception) {
                        emptyList()
                    }
                }
            }
        } catch (e: Exception) {
            try {
                val roomRepo = getBookingRoomRepoSafely()
                val local = roomRepo?.getUserBookings(userId)
                if (!local.isNullOrEmpty()) local else emptyList()
            } catch (_: Exception) {
                emptyList()
            }
        }

    private fun getBookingRoomRepoSafely(): com.students.salonapp.data.local.BookingRoomRepository? {
        return try {
            com.students.salonapp.App.instance?.bookingRoomRepo
        } catch (_: Exception) {
            null
        }
    }

    private fun getServiceRoomRepoSafely(): com.students.salonapp.data.local.ServiceRoomRepository? {
        return try {
            com.students.salonapp.App.instance?.serviceRoomRepo
        } catch (_: Exception) {
            null
        }
    }
    private fun getMasterRoomRepoSafely(): com.students.salonapp.data.local.MasterRoomRepository? {
        return try {
            com.students.salonapp.App.instance?.masterRoomRepo
        } catch (_: Exception) {
            null
        }
    }
}