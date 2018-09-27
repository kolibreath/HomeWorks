//
// Created by kolibreath on 18-5-20.
//

#include "CPage.h"
#include <fstream>
void Cpage::setCircle(Circle circle){
    mCircleVector.push_back(circle);
}

void Cpage::setRect(Rect graph) {
    mRectVector.push_back(graph);
}

void Cpage::setCirRect(CirRect cirRect){
    mCirRectVector.push_back(cirRect);
}

vector<CirRect> Cpage::getCirRect() {
    return  mCirRectVector;
}

vector<Circle> Cpage::getCircleVector(){
   return mCircleVector;
}

vector<Rect>   Cpage::getRectVector(){
   return mRectVector;
}

void Cpage::writePage() {
    ofstream out("/home/kolibreath/CLionProjects/untitled/something",ios::trunc | ios::out);
    for(int i=0;i<mRectVector.size();i++){
        out<<"rect "<<i<<" width is "<<mRectVector[i].getWidth()<<" the height is "<<mRectVector[i].getHeight()<<endl;
    }
    for(int i=0;i<mCircleVector.size();i++){
        out<<"Circle "<<i<<" point x is "<<mCircleVector[i].getPoint().x<<" y is "<<mCircleVector[i].getPoint().y
                <<"the radius is "<<mCircleVector[i].radius
           <<endl;
    }
    for(int i=0;i<mCirRectVector.size();i++){
        out<<"circleRect "<<i<<" width is "<<mCirRectVector[i].getWidth()<<" the height is "
                                                                           <<mCirRectVector[i].getHeight()
           <<"the radius is "<<mCirRectVector[i].getRadius()<<endl;
    }
    out.close();
}

void Cpage::showPage() {
    ifstream in("/home/kolibreath/CLionProjects/untitled/something",ios::in);
    string string1;
    in>>string1;
    cout<<string1<<endl;
}
