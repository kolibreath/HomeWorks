cmake_minimum_required(VERSION 3.10)
project(untitled)

set(CMAKE_CXX_STANDARD 11)

add_executable(untitled main.cpp CCircle.h CCircle.cpp CCirRect.cpp CCircle.h CGraph.cpp CGraph.h
        CRect.cpp CRect.h CPage.cpp CCircle.h)