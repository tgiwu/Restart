//
// Created by Yang Zhang on 2019/3/19.
//

#include "utils.h"

std::string jstring2str(JNIEnv * env, jstring str) {
    char* rtn = NULL;
    jclass  clsstring = env ->FindClass("java/lang/String");
    jstring strencode = env -> NewStringUTF("UTF-8");
    jmethodID  mid = env -> GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray  barr = (jbyteArray) env -> CallObjectMethod(str, mid, strencode);
    jsize alen = env -> GetArrayLength(barr);
    jbyte * ba = env -> GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char*) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env -> ReleaseByteArrayElements(barr, ba, 0);
    std::string stemp(rtn);
    free(rtn);
    return stemp;
}

jstring str2jstring(JNIEnv * env, const char* pat) {
    jclass strClass = env ->FindClass("Ljava/lang/String");
    jmethodID ctorID = env -> GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    jbyteArray  bytes = env -> NewByteArray(strlen(pat));
    env -> SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
    jstring encoding = env -> NewStringUTF("GB2312");
    return (jstring)(env) ->NewObject(strClass, ctorID, bytes, encoding);
}