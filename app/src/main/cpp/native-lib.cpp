#include <jni.h>
#include <string>
#include "base64.h"
#include <android/log.h>

extern "C" JNIEXPORT jstring JNICALL
 Java_com_example_restart_ui_QuoteActivity_sayHello(
         JNIEnv *env,
         jobject) {

     std::string myHello = "my hello";
     return env -> NewStringUTF(myHello.c_str());
 }

extern "C" JNIEXPORT jstring JNICALL
 Java_com_example_restart_data_FakeQuoteDao_textEncrypt(
         JNIEnv *env,
         jobject,
         jstring jstr) {

//    PrintStart();

    size_t length = (size_t) env ->GetStringUTFLength(jstr);
    unsigned char *c_msg = NULL;
    c_msg = (unsigned char*) env -> GetStringUTFChars(jstr, 0);

    char* result = NULL;
    result = b64_encode(c_msg, length);

    return env->NewStringUTF(result);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_restart_data_FakeQuoteDao_textDecrypt(
        JNIEnv *env,
        jobject,
        jstring jstr) {

//    PrintStart();

    size_t length = (size_t) env ->GetStringUTFLength(jstr);

    char *c_msg = NULL;
    c_msg = (char*) env ->GetStringUTFChars(jstr, 0);

    unsigned char* result = NULL;
    result = b64_decode(c_msg, length);

    return env->NewStringUTF((const char*) result);
}


