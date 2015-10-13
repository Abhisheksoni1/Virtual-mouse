
import com.googlecode.javacpp.Loader;
import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;
import static com.googlecode.javacv.cpp.opencv_core.CV_WHOLE_SEQ;
import com.googlecode.javacv.cpp.opencv_core.CvContour;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.cvDrawContours;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_LINK_RUNS;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_RETR_LIST;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvContourArea;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvFindContours;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGetCentralMoment;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGetSpatialMoment;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvMoments;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abhishek
 */
public class ccmFilter {
    public static int t; 
    public static IplImage Filter(IplImage img, IplImage imghsv,IplImage imgbin, CvScalar maxc,CvScalar minc,CvSeq contour1, CvSeq contour2,CvMemStorage storage, CvMoments moments, int b, int g,int r ) throws AWTException
    {
      double moment10,moment01, areamax,areac=0, m_area;
      int posx=0, posy=0;
      Robot rbt = new Robot();
      
      cvCvtColor(img,imghsv,CV_BGR2HSV);
      cvInRangeS(imghsv,minc,maxc,imgbin);
      
      areamax = 1000;
      
      cvFindContours(imgbin,storage,contour1,Loader.sizeof(CvContour.class),CV_RETR_LIST,CV_LINK_RUNS,cvPoint(0,0));
      
        contour2 = contour1;
         while(contour1 !=null && !contour1.isNull())
         {
             areac = cvContourArea(contour1,CV_WHOLE_SEQ,1);
             if(areac>areamax)
             {
                 areamax = areac;
             }   
                 contour1 = contour1.h_next();
         }
        while(contour2 !=null && !contour2.isNull())
        {
          areac = cvContourArea(contour2,CV_WHOLE_SEQ,1);
          
          if(areac<areamax) 
          {
            cvDrawContours(imgbin,contour2,CV_RGB(0,0,0),CV_RGB(0,0,0),0,CV_FILLED,8,cvPoint(0,0));
            
          }
          contour2=contour2.h_next();
          
        }
        
            cvMoments(imgbin,moments,1);
            
            moment10 = cvGetSpatialMoment(moments,1,0);
            moment01 = cvGetSpatialMoment(moments,0,1);
            m_area = cvGetCentralMoment(moments,0,0);
            
            posx= (int) (moment10/m_area);
            posy= (int) (moment01/m_area);
            
           if(b==1)
              if(posx>0 && posy>0)
              {
                  rbt.mouseMove(posx*4, posy*3);
              }
           
           if(g==1)
              if(posx>0 && posy>0)
              {
                  rbt.mousePress(InputEvent.BUTTON1_MASK);
                  t++;
              }
           
              else if(t>0)
              {
                  rbt.mouseRelease(InputEvent.BUTTON1_MASK);
                  t=0;
              }
          
        
        
        
        return imgbin; 
    }        
    
}

