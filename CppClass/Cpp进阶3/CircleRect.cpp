#include <iostream>
#include "Graph.h"
#include "Circle.h"
#include "Rect.h"
#include "CircleRect.h"

using namespace std;

CCircleRect::CCircleRect(CRect& rect, CCircle& circle,int color,int linewidth)
:CRect(rect),CCircle(circle),CGraph(3,color,linewidth){

}


void CCircleRect::showMessage(){

    CGraph::show();
    CRect::showMessage();
    CCircle::showMessage();
}


void CCircleRect::save(fstream &file){

    CGraph::save(file);
    CRect::saveP(file);
    CCircle::saveP(file);

}

void CCircleRect::load(fstream &file){

    CRect::load(file);
    CCircle::load(file);

}