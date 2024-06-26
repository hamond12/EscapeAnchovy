import 'dart:async';
import 'dart:math';

import 'package:escape_anchovy/src/screen/home/home_screen.dart';
import 'package:escape_anchovy/src/screen/user_name/user_name_screen.dart';
import 'package:escape_anchovy/src/util/shared_preferences_util.dart';
import 'package:flutter/material.dart';

class SplashController with ChangeNotifier {
  double anchovyTopPos = 60;

  void moveUp() {
    Timer.periodic(const Duration(milliseconds: 50), (timer) {
      anchovyTopPos -= 2;
      if (timer.tick >= (3 * 1000) / 50) {
        timer.cancel();
      }
      notifyListeners();
    });
  }

  final List<String> famousSaying = [
    '운동을 위해 시간을 내지 않으면\n병 때문에 시간을 내야할지도 모른다.',
    '독서는 마음을 위한 것이고\n운동은 몸을 위한 것이다.',
    '시간이 나서 운동하는게 아니라\n 시간을 내서 운동해야 한다.',
    '한계라고 느낄 때\n\'한 개\'를 더해야 성장한다.',
    '몸의 발전이\n마음의 발전을 이룬다.',
    '오늘 당신이 느끼는 고통은\n내일 당신이 느낄 힘이 될 것이다.',
    '정확하게 반복하고\n허세없이 운동해라.',
    '지금은 이 운동이 힘들지만\n언젠가는 워밍업이 될 것이다.',
    '인생은 당신이 가장 편안한 환경에서\n빠져나올 때부터 시작된다.',
    '그만두고 싶다는 생각이 들면\n왜 시작했는지 생각하여 보라.',
    '처음부터 다시 시작하는 것에 실증이 났다면\n하고 있는 일을 포기하지 않으면 된다.',
    '남들이 그만둘 때\n난 계속한다.',
    '포기는 선택이지\n운명이 아니다.'
  ];

  int randomNumber = Random().nextInt(12);

  double returnFamousSayingSize() {
    switch (randomNumber) {
      case 0:
      case 2:
      case 5:
      case 7:
      case 8:
      case 9:
        return 20;
      case 1:
      case 3:
      case 6:
        return 22;
      case 4:
        return 24;
      case 10:
        return 18;
      case 11:
        return 24;
      default:
        return 24;
    }
  }

  Future<bool> isNameInput() async {
    final inputName = SharedPreferencesUtil.getBool('inputName')??false;
    return inputName;
  }

  Future<void> checkInputName(BuildContext context) async {
    await Future.delayed(const Duration(seconds: 3));

    isNameInput().then((value) async {
      if (value) {
        Navigator.pushNamedAndRemoveUntil(
            context, HomeScreen.routeName, (route) => false);
      } else {
        Navigator.pushNamedAndRemoveUntil(
            context, UserNameScreen.routeName, (route) => false);
      }
    });
  }
}
