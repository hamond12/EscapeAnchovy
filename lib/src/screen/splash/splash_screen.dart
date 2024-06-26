import 'package:escape_anchovy/res/text/styles.dart';
import 'package:escape_anchovy/src/screen/splash/splash_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  static const routeName = '/splash';

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  final _controller = SplashController();

  @override
  void initState() {
    super.initState();
    _controller.moveUp();
    _controller.checkInputName(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0XFFCCE9FF),
      body: AnimatedBuilder(
          animation: _controller,
          builder: (context, snapshot) {
            return _buildPage(context);
          }),
    );
  }

  Widget _buildPage(BuildContext context) {
    double screenHeight = MediaQuery.of(context).size.height;
    return Stack(
      children: [
        Positioned(
            top: screenHeight * 0.15,
            left: 0,
            right: 0,
            child: Text('ESCAPE\nENCHOVY',
                textAlign: TextAlign.center,
                style:
                    TextStyles.title.copyWith(color: const Color(0XFF8A848D)))),
        Positioned(
            bottom: 0,
            top: _controller.anchovyTopPos,
            right: 30,
            left: 0,
            child: SvgPicture.asset(
              'asset/svg/anchovy.svg',
              fit: BoxFit.scaleDown,
            )),
        Positioned(
            bottom: 0,
            left: 0,
            right: 0,
            child: SvgPicture.asset('asset/svg/wave.svg')),
        Positioned(
          bottom: screenHeight * 0.25,
          left: 0,
          right: 0,
          child: Center(
            child: Transform.rotate(
              angle: -3 * (3.141592653589793 / 180),
              child: Text(_controller.famousSaying[_controller.randomNumber],
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontFamily: 'Pretendard',
                      fontSize: _controller.returnFamousSayingSize(),
                      color: Colors.white,
                      fontWeight: FontWeight.w500)),
            ),
          ),
        ),
        Positioned(
          bottom: 18,
          left: 0,
          right: 0,
          child: Center(
            child: Text('Created by Hamond',
                style: TextStyles.b3Regular.copyWith(color: Colors.black)),
          ),
        ),
      ],
    );
  }
}
