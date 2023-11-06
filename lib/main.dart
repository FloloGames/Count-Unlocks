import 'dart:async';
import 'package:count_unlocks/android_count_unlocks_manager.dart';
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
  AndroidCountUnlocksManager androidCountUnlocksManager =
      AndroidCountUnlocksManager();
  String debugText = "";

  void callTestMethod() {
    androidCountUnlocksManager.callTestMethod();
  }

  void getUnlockCount() async {
    int? unlockCount = await androidCountUnlocksManager.getUnlockCount();
    unlockCount ??= -1;
    debugText = "Unlock Count: $unlockCount";
    setState(() {});
  }

  @override
  void initState() {
    super.initState();

    // // Retrieve parameters from the Intent
    // Map<String, dynamic> args =
    //     ModalRoute.of(context)!.settings.arguments as Map<String, dynamic>;

    // if (args != null) {
    //   // Process the parameters
    //   param1 = args["param1"] ?? "";
    //   param2 = args["param2"] ?? 0;
    // }

    getUnlockCount();
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
                    callTestMethod();
                  },
                ),
                ElevatedButton(
                  onPressed: getUnlockCount,
                  child: const Text("GetUnlockCount"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
