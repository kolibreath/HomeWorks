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

 void CRect::Show(){

    cout<<endl<<"\t Point:"<<Pt.X<<","<<Pt.Y <<" \t Width: "<<Width
       <<"\t  Height: "<<Height<<endl;
    }

void CRect::ShowMsg(){

    CGraph::Show();
    Show();
}


void CRect::SaveP(fstream& file){
    
    file.write((char*)&Pt.X,sizeof(Pt.X));
	file.write((char*)&Pt.Y,sizeof(Pt.Y));
	file.write((char*)&Height,sizeof(Height));
	file.write((char*)&Width,sizeof(Width));
}

void CRect::Load(fstream& file){
    
    file.read((char*)&Pt.X,sizeof(Pt.X));
	file.read((char*)&Pt.Y,sizeof(Pt.Y));
	file.read((char*)&Height,sizeof(Height));
	file.read((char*)&Width,sizeof(Width));
}

void CRect::Save(fstream& file){

    CGraph::Save(file);
    SaveP(file);
}