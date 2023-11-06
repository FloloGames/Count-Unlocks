import 'package:flutter/services.dart';

class AndroidCountUnlocksManager {
  static const _testMethodChannel = MethodChannel("testChannel");
  // static const _testCBMethodChannel = MethodChannel("testCBChannel");
  // int _deviceUnlocks = 0;

  static const String _testMethodName = "testMethod";
  // static const String _testCBMethodName = "testCBMethod";
  static const String _getUnlockCountMethodName = "getUnlockCount";

  AndroidCountUnlocksManager() {
    // testCBMethodChannel.setMethodCallHandler((call) {
    //   if (call.method == testCBMethodName) {
    //     debugText = call.arguments.toString();
    //     setState(() {});
    //   }
    //   return Future.value();
    // });
  }

  Future<void> callTestMethod() {
    return _testMethodChannel.invokeMethod(_testMethodName);
  }

  Future<int?> getUnlockCount() {
    return _testMethodChannel.invokeMethod<int>(_getUnlockCountMethodName);
  }
}
