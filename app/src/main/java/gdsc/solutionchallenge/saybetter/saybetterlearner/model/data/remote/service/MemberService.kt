package gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.service

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.local.RequestEntity.member.MemberInfoRequest
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.GeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGeneralResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.dto.member.MemberGetResponse
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.retrofit.MemberRetrofitInterface
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member.MemberCodeView
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member.MemberGetView
import gdsc.solutionchallenge.saybetter.saybetterlearner.model.data.remote.view.member.MemberInfoView
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.imageSelect.GetImageUri
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.module.getRetrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MemberService {
    private lateinit var memberInfoView: MemberInfoView
    private lateinit var memberGetView: MemberGetView
    private lateinit var memberCodeView: MemberCodeView

    fun setMemberInfoView(memberInfoView: MemberInfoView) {
        this.memberInfoView = memberInfoView
    }

    fun setMemberGetView(memberGetView: MemberGetView) {
        this.memberGetView = memberGetView
    }

    fun setMemberCodeView(memberCodeView: MemberCodeView) {
        this.memberCodeView = memberCodeView
    }

    fun postMemberInfo(accessToken : String,
                       context: Context,
                       profileImageUri: Uri?,
                       memberInfoRequest: MemberInfoRequest) {
        profileImageUri?.let { uri ->
            val file = File(GetImageUri().absolutelyPath(uri, context))

            // 파일을 MultipartBody.Part로 변환
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            Log.d("requestFile ", file.absolutePath)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val gson = Gson()
            val memberInfoBody = gson.toJson(memberInfoRequest).toRequestBody("application/json".toMediaTypeOrNull())

            val memberService = getRetrofit().create(MemberRetrofitInterface::class.java)
            memberService.postMemberInfo("Bearer $accessToken", body, memberInfoBody)
                .enqueue(object : Callback<GeneralResponse<String>> {
                    override fun onResponse(
                        call: Call<GeneralResponse<String>>,
                        response: Response<GeneralResponse<String>>
                    ) {
                        if (response.isSuccessful) {
                            val resp: GeneralResponse<String>? = response.body()
                            if (resp != null) {
                                when (resp.code) {
                                    "SUCCESS_200" -> memberInfoView.onPostMemberInfoSuccess(resp)       //변경
                                    else -> memberInfoView.onPostMemberInfoFailure(
                                        resp.isSuccess,
                                        resp.code,
                                        resp.message
                                    )
                                }
                            } else {
                                Log.e("postMemberInfo", "Response body is null")
                            }
                        } else {
                            Log.e("postMemberInfo", "Response not successful: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponse<String>>, t: Throwable) {
                        Log.d("postMemberInfo", t.toString())
                    }
                })
        }
    }

    fun getMemberInfo(accessToken : String, ) {
        val memberService = getRetrofit().create(MemberRetrofitInterface::class.java)
        memberService.getMemberInfo("Bearer " + accessToken).enqueue(object : Callback<GeneralResponse<MemberGetResponse>> {
                    override fun onResponse(
                        call: Call<GeneralResponse<MemberGetResponse>>,
                        response: Response<GeneralResponse<MemberGetResponse>>
                    ) {
                        if (response.isSuccessful) {
                            val resp: GeneralResponse<MemberGetResponse>? = response.body()
                            if (resp != null) {
                                when (resp.code) {
                                    "SUCCESS_200" -> memberGetView.onGetMemberInfoSuccess(resp)       //변경
                                    else -> memberGetView.onGetMemberInfoFailure(
                                        resp.isSuccess,
                                        resp.code,
                                        resp.message
                                    )
                                }
                            } else {
                                Log.e("getMemberInfo", "Response body is null")
                            }
                        } else {
                            Log.e("getMemberInfo", "Response not successful: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponse<MemberGetResponse>>, t: Throwable) {
                        Log.d("getMemberInfo", t.toString())
                    }
                })
        }

    fun getMemberCode(accessToken : String) {
        val memberService = getRetrofit().create(MemberRetrofitInterface::class.java)
        Log.d("getMemberCode : " , accessToken)
        memberService.getMemberCode("Bearer " + accessToken).enqueue(object : Callback<GeneralResponse<String>> {
            override fun onResponse(call: Call<GeneralResponse<String>>, response: Response<GeneralResponse<String>>) {
                if (response.isSuccessful) {
                    val resp: GeneralResponse<String>? = response.body()
                    if (resp != null) {
                        when (resp.code) {
                            "SUCCESS_200" -> memberCodeView.onGetMemberCodeSuccess(resp)
                            else -> memberCodeView.onGetMemberCodeFailure(resp.isSuccess, resp.code, resp.message)
                        }
                    } else {
                        Log.e("getMemberCode", "Response body is null")
                    }
                } else {
                    Log.e("getMemberCode", "Response not successful: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<GeneralResponse<String>>, t: Throwable) {
                Log.d("getMemberCode", t.toString())
            }
        })
    }
}
