#pragma once
#include<iostream>
#include<string>
using namespace std;

struct point {
	int x;
	int y;
};



class CGraph {
public:
	int linewidth;
	string type;
	string color;
	point pos;

	CGraph();

	virtual void setGraph();

	virtual void show() = 0;
};