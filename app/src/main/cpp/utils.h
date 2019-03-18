//
// Created by Yang Zhang on 2019/3/19.
//
#include <jni.h>
#include <string>

std::string jstring2str(JNIEnv * env, jstring str);
jstring str2jstring(JNIEnv * env, const char* pat);

