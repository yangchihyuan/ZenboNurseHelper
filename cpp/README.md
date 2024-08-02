This folder contains the code files for the server side program of ZenboNurseHelper. It provides an Graphic User Interface (GUI) for a user to remotely control the robot's action. The GUI currently looks like the image below and allows a user to send commands to the robot-side's app, which calls Zenbo SDK to execute those commands.
<img src="GUI.jpg" alt="GUI" height="250"/>

In this project, I utilize Intel OpenVINO's human_pose_estimation_demo in their Open Model Zoo 2024 demos as a tool to guide our Zenbo robot. My server-side program receives frames transmitted from the robot-side app, estimates human pose landmark coordinates, and reports the results to the robot-side program.

# Environment Setting
- Ubuntu 22.04
- Intel OpenVINO toolkit 2024.1.0
- libboost-dev 1.74.0.3ubuntu7
- libprotobuf-dev 3.12.4-1ubuntu7.22.04.1
- libopencv-dev 4.5.4+dfsg-9ubuntu4
- libgflags-dev 2.2.2-2

# OpenVINO Setting
Please follow Intel OpenVINO 2024.1.0's [instruction](https://docs.openvino.ai/2024/index.html) to install the library. OpenVINO 2024.1.0 does not support Ubuntu 24.04, which is the reason I use Ubuntu 22.04.
We need Intel OpenVINO Toolkit Open Model Zoo as a framework. i git clone it from its GitHub repository.
```
cd ~
git clone https://github.com/openvinotoolkit/open_model_zoo.git
```
We need a pretrained model human-pose-estimation-0001.xml used in the human_pose_estimation_demo, which is a part of the OpenPose algorithm.
```
python3 ~/open_model_zoo/tools/model_tools/src/omz_tools/omz_downloader.py --list ~/open_model_zoo/demos/human_pose_estimation_demo/cpp/models.lst -o ~/open_model_zoo/models
```

# Installation
Suppose your Open Model Zoo is installed in ~/open_model_zoo.
Please git clone this repository into the demos directory.
```
cd ~/open_model_zoo/demos
git clone https://github.com/yangchihyuan/RobotVideoSummary_ServerSide.git
```

# Compile
Run the OpenVINO's build_demos.sh in ~/open_model_zoo/demos to build this project, and an executable file 9_NurseHelper should be created at ~/omz_demos_build/intel64/Release/
Set the permissions as executable for the run_server_side_program.sh in the RobotVideoSummary_ServerSide directory.
```
cd ~/open_model_zoo/demos/RobotVideoSummary_ServerSide/cpp
chmod +x run_server_side_program.sh
```
Run the shell script.
```
./run_server_side_program.sh
```
To terminate this program, press Ctrl+C


The main function is in the Main.cpp, which creates three threads. The 1st receives images through a socket, the 2nd estimates human poses, and the 3rd reports human key points to the Zenbo robot through another socket.

