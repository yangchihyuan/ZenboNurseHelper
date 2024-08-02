cmake_minimum_required(VERSION 3.5)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

#The three settings used by Qt
set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

find_package(Protobuf REQUIRED)
find_package(Boost COMPONENTS REQUIRED)   #I don't specify components, I need to revise this statement
find_package(OpenCV REQUIRED COMPONENTS core highgui videoio imgproc imgcodecs)
list(APPEND CMAKE_PREFIX_PATH "/home/chihyuan/Qt/6.7.2/gcc_64/")
find_package(QT NAMES Qt6 Qt5 REQUIRED COMPONENTS Widgets Network Multimedia)
find_package(Qt${QT_VERSION_MAJOR} REQUIRED COMPONENTS Widgets Network Multimedia)

#find_package(PkgConfig REQUIRED)
#pkg_check_modules(GTK3 REQUIRED gtk+-3.0)

#include_directories(${GTK3_INCLUDE_DIRS})
#link_directories(${GTK3_LIBRARY_DIRS})
#add_definitions(${GTK3_CFLAGS_OTHER})


set(PROTO_FILE ServerSend.proto)
protobuf_generate_cpp(PROTO_SRC PROTO_HDR ${PROTO_FILE})
file(GLOB_RECURSE HEADERS ${CMAKE_CURRENT_SOURCE_DIR}/*.hpp)
file(GLOB GGML_OBJECT /home/chihyuan/whisper.cpp/ggml/src/*.o)
file(GLOB EXAMPLES_OBJECT /home/chihyuan/whisper.cpp/examples/*.o)
set(SOURCES 
            JPEG.cpp
            utility_directory.cpp
            utility_TimeRecorder.cpp
            utility_compute.cpp
            utility_string.cpp
            utility_csv.cpp
            main.cpp
            human_pose_estimator.cpp
            Pose.cpp
            ${PROTO_SRC}
            mainwindow.cpp
            mainwindow.h
            mainwindow.ui
            ProcessImageThread.cpp
            SendCommandThread.cpp
            ProcessAudioThread.cpp
    )

#set_property(SOURCE SendToRobot.cpp PROPERTY SKIP_AUTOMOC ON)

add_demo(NAME 9_NurseHelper
         SOURCES ${SOURCES}
         HEADERS ${HEADERS}
         DEPENDENCIES monitors models pipelines portaudio
         INCLUDE_DIRECTORIES "${CMAKE_CURRENT_SOURCE_DIR}/include" "/home/chihyuan/whisper.cpp/spm-headers"
         )

#enable debug information
set( CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g")   


include_directories(${PROTOBUF_INCLUDE_DIRS})
get_filename_component(PROTO_HDR_DIRECTORY ${PROTO_HDR} DIRECTORY)
include_directories(${PROTO_HDR_DIRECTORY})
include_directories(OpenCV_INCLUDE_DIR)

link_directories(${LIB_FOLDER} /usr/local/lib)

set_target_properties(${TARGET_NAME} PROPERTIES "CMAKE_CXX_FLAGS" "${CMAKE_CXX_FLAGS}")

target_link_libraries(${OMZ_DEMO_NAME} PRIVATE ${PROTOBUF_LIBRARIES}
    Qt${QT_VERSION_MAJOR}::Widgets Qt${QT_VERSION_MAJOR}::Network
    Qt${QT_VERSION_MAJOR}::Multimedia ${GGML_OBJECT} ${EXAMPLES_OBJECT}
    gomp "/home/chihyuan/whisper.cpp/src/whisper.o")
#target_link_libraries(${OMZ_DEMO_NAME} PRIVATE ${PROTOBUF_LIBRARIES} ${GTK3_LIBRARIES})
