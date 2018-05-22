#pragma once
#include"CGraph.h"

class Rect:public CGraph {
public:
	int width;
	int height;

	Rect();
	Rect(string string1);
	void setRect();
	virtual void show();

	int getWidth();
	int getHeight();

};