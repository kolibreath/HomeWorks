#pragma once
#include"CGraph.h"

class Circle:public CGraph {
public:
	int radius;

	Circle(string type);

	Circle();

	Circle(int r);

	void setCircle();

	virtual void show();

	point getPoint();
};