package online.interview.teamtweaks.viewmodel.user.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.emperor.kotlinexample.utils.Resource
import com.interview.jumpstartninja.repository.UserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

private const val TAG = "UserViewModel"

class PostmageViewModel : ViewModel(), CoroutineScope {
    private val job = Job()// Should cancel this on Destroy

    fun postImage(image:ByteArray) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val data = UserRepo.postImage(image)
            emit(Resource.success(data = data))
        } catch (exception:Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}