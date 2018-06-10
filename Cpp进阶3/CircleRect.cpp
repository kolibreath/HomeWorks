#include <iostream>
#include "Graph.h"
#include "Circle.h"
#include "Rect.h"
#include "CircleRect.h"

using namespace std;

CCircleRect::CCircleRect(CRect& rect, CCircle& circle,int color,int linewidth)
:CRect(rect),CCircle(circle),CGraph(3,color,linewidth){

}

 void CCircleRect::Show(){

    }

void CCircleRect::ShowMsg(){

    CGraph::Show();
    CRect::Show();
    CCircle::Show();
    Show();
}


void CCircleRect::Save(fstream& file){

    CGraph::Save(file);
	CRect::SaveP(file);
    CCircle::SaveP(file);

}

void CCircleRect::Load(fstream& file){
    
	CRect::Load(file);
    CCircle::Load(file);

}