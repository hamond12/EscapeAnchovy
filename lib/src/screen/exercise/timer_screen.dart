import 'package:escape_anchovy/res/text/colors.dart';
import 'package:escape_anchovy/res/text/styles.dart';
import 'package:escape_anchovy/src/common/common_app_bar.dart';
import 'package:escape_anchovy/src/screen/exercise/exercise_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class TimerScreen extends StatefulWidget {
  const TimerScreen({super.key, required this.exerciseController});

  static const routeName = '/timer';

  final ExerciseController exerciseController;

  @override
  State<TimerScreen> createState() => _TimerScreenState();
}

class _TimerScreenState extends State<TimerScreen> {
  final _controller = ExerciseController();

  @override
  void initState() {
    super.initState();
      _controller.moveScreen(context, widget.exerciseController.set);
    
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CommonAppBar(title: '휴식시간', isExercise: true),
      body: AnimatedBuilder(
          animation: _controller,
          builder: (context, snapshot) {
            return _buildPage(context);
          }),
    );
  }

  Widget _buildPage(BuildContext context) {
    return PopScope(
      canPop: false,
      child: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(height: MediaQuery.of(context).size.height * 0.14),
            SvgPicture.asset('asset/svg/timer.svg'),
            const SizedBox(
              height: 24,
            ),
            Text(
              _controller.formatTime(_controller.seconds),
              style: TextStyles.title.copyWith(fontSize: 50),
            ),
            const SizedBox(
              height: 40,
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  (7 - widget.exerciseController.set).toString(),
                  style: TextStyles.h1Medium.copyWith(
                      color: context.isLight
                          ? LightModeColors.yellow
                          : DarkModeColors.yellow),
                ),
                const Text(
                  '세트 남았습니다.',
                  style: TextStyles.h1Medium,
                )
              ],
            )
          ],
        ),
      ),
    );
  }
}
