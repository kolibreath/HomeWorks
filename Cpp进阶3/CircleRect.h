#ifndef __RECTCIRCLE__
#define __RECTCIRCLE__

#include "Graph.h"
#include "Rect.h"
#include "Circle.h"

class CCircleRect:public CRect,public CCircle{

private :

public:
    CCircleRect(CRect&,CCircle &,int color=0,int linewidth=1);

	CCircleRect(int color=0,int linewidth=1):CGraph(1,color,linewidth){};

    void show();

    virtual void showMessage();

	void save(fstream &file);

	void load(fstream &file);

};

#endif
