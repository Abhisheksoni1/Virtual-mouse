import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_core.CvArr;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;                                                                                                                                       
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvOr;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvScalar;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_ANY;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_FRAME_WIDTH;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvCreateCameraCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvQueryFrame;
import static com.googlecode.javacv.cpp.opencv_highgui.cvReleaseCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSetCaptureProperty;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;
import java.awt.AWTException;
public class virtual_mouse {

    /**
     *
     * @param args
     * @throws AWTException
     */
    public static void main(String[] args) throws AWTException {
        IplImage img1,imgbinB,imgbinG,imgbinR,imgd;
        IplImage imghsv;
        CvScalar Bminc = cvScalar(95,150,75,0),Bmaxc = cvScalar(145,255,255,0);
        CvScalar Gminc = cvScalar(40,50,60,0),Gmaxc = cvScalar(80,255,255,0);
        CvScalar Rminc = cvScalar(164,229,139,0),Rmaxc = cvScalar(179,255,255,0);
        CvArr mask;
        int w =320,h=240;
        imghsv = cvCreateImage(cvSize(w,h),8,3);
        imgbinG = cvCreateImage(cvSize(w,h),8,1);
        imgbinB = cvCreateImage(cvSize(w,h),8,1);
        imgbinR = cvCreateImage(cvSize(w,h),8,1);
        IplImage imgc = cvCreateImage(cvSize(w,h),8,1);
        imgd = cvCreateImage(cvSize(w,h),8,1);
        CvSeq contour1 =new CvSeq();
        CvSeq contour2 = null;
        CvMemStorage storage = CvMemStorage.create();
        CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));
        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
        cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_WIDTH,w);
        cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_HEIGHT,h);
        while(true)
        {
           img1 = cvQueryFrame(capture1);
           if(img1==null)
           {
               System.err.println("NO IMAGE");
               break;
           }
           
         imgbinB = ccmFilter.Filter(img1,imghsv,imgbinB,Bmaxc,Bminc,contour1,contour2,storage,moments,1,0,0);
         imgbinG = ccmFilter.Filter(img1,imghsv,imgbinG,Gmaxc,Gminc,contour1,contour2,storage,moments,0,1,0);
         imgbinR= ccmFilter.Filter(img1,imghsv,imgbinR,Rmaxc,Rminc,contour1,contour2,storage,moments,0,0,1);
         cvOr(imgbinB,imgbinG,imgc,mask=null);
         //cvOr(imgbinR,imgc,imgd,mask=null);
          cvShowImage("colour_detect",imgc);
           cvShowImage("REAL",img1);
           
           char c = (char)cvWaitKey(15);
           if(c==27)
               break;
        }
        
      cvReleaseImage(imghsv);
      cvReleaseImage(imgbinG);
      cvReleaseImage(imgbinB);
      cvReleaseImage(imghsv);
      cvReleaseMemStorage(storage);
      cvReleaseCapture(capture1);
        
        
    }
    
}
