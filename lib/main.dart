import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
// import 'package:path_provider/path_provider.dart';
// import 'package:shared_preferences/shared_preferences.dart';
// import 'package:workmanager/workmanager.dart';

void main() => runApp(const MyApp());

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  static const testMethodChannel = MethodChannel("testChannel");
  static const testCBMethodChannel = MethodChannel("testCBChannel");
  // int _deviceUnlocks = 0;

  static const String testMethodName = "testMethod";
  static const String testCBMethodName = "testCBMethod";

  String debugText = "";

  void callTestMethod() {
    testMethodChannel.invokeMethod(testMethodName);
  }

  @override
  void initState() {
    super.initState();

    // // Retrieve parameters from the Intent
    // Map<String, dynamic> args = ModalRoute.of(context)!.settings.arguments as Map<String, dynamic>;

    // if (args != null) {
    //   // Process the parameters
    //   param1 = args["param1"] ?? "";
    //   param2 = args["param2"] ?? 0;
    // }

    testCBMethodChannel.setMethodCallHandler((call) {
      if (call.method == testCBMethodName) {
        print("testCBMethodCalled");
        debugText = call.arguments.toString();
        setState(() {});
      }
      return Future.value();
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text("Flutter WorkManager Example"),
        ),
        body: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Text(
                  //"Device Unlocks: $_deviceUnlocks",
                  "TestChannelMethods",
                  style: Theme.of(context).textTheme.headlineSmall,
                ),
                Text(
                  debugText,
                  style: Theme.of(context).textTheme.headlineSmall,
                ),
                ElevatedButton(
                  child: const Text("Test.."),
                  onPressed: () {
                    print("Testing started...");
                    // print("does nothing jet");
                    callTestMethod();
                    print("Testing ended.");
                  },
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
