//
// Created by kolibreath on 18-5-20.
//

#ifndef UNTITLED_CPAGE_H
#define UNTITLED_CPAGE_H

#endif //UNTITLED_CPAGE_H

#include "CGraph.h"
#include "CCircle.h"
#include "CRect.h"
#include "CCirRect.h"
#include <iostream>
#include <vector>
using namespace std;

class Cpage{

public :
    vector<CGraph> mVector;
    vector<Circle> mCircleVector;
    vector<Rect> mRectVector;
    vector<CirRect> mCirRectVector;

    int x , y;

public:
    void setCircle(Circle circle);
    void setRect(Rect graph);
    void setCirRect(CirRect cirRect);

    vector<Circle> getCircleVector();
    vector<Rect>   getRectVector();
    vector<CirRect> getCirRect();

    Cpage(int length, int width){
        this->x = length;
        this->y = width;
    }

    void showPage();
    void writePage();

};