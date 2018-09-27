#include <iostream>
#include "Graph.h"
#include "Rect.h"
using namespace std;

CRect::CRect(Point & pt, int width , int height,int color,int linewidth)
:CGraph(2,color,linewidth){
     Pt = pt;
     Width = width;
     Height = height;
}

CRect::CRect(CRect& rect):CGraph(2){
     Pt = rect.Pt;
     Width = rect.Width;
     Height = rect.Height;
}


void CRect::showMessage(){

    CGraph::show();
    cout<<endl<<"\t Point:"<<Pt.X<<","<<Pt.Y <<" \t Width: "<<Width
       <<"\t  Height: "<<Height<<endl;
}


void CRect::saveP(fstream &file){
    
    file.write((char*)&Pt.X,sizeof(Pt.X));
	file.write((char*)&Pt.Y,sizeof(Pt.Y));
	file.write((char*)&Height,sizeof(Height));
	file.write((char*)&Width,sizeof(Width));
}

void CRect::load(fstream &file){
    
    file.read((char*)&Pt.X,sizeof(Pt.X));
	file.read((char*)&Pt.Y,sizeof(Pt.Y));
	file.read((char*)&Height,sizeof(Height));
	file.read((char*)&Width,sizeof(Width));
}

void CRect::save(fstream &file){

    CGraph::save(file);
    saveP(file);
}