import com.sun.jna.ptr.IntByReference;
import com.weaver.inte.jna.common.data.AudioFrameV2Data;
import com.weaver.inte.jna.common.data.AudioFrameV3Data;
import com.weaver.inte.jna.common.data.MetadataFrameData;
import com.weaver.inte.jna.common.data.NdiSourceData;
import com.weaver.inte.jna.common.data.VideoFrameV2Data;
import com.weaver.inte.jna.find.FindData;
import com.weaver.inte.jna.find.FindLib;
import com.weaver.inte.jna.receive.ReceiveData;
import com.weaver.inte.jna.receive.ReveiveLib;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.List;


public class ApplicationReceiveMain {

    public static void main(String[] args) throws IOException {
//        FindData findData = new FindData(true, "Memo", "192.168.1.128");
        FindData findData = new FindData(true, "", "");
        try (FindLib findLib = new FindLib(findData)) {
            //查找NDI源
            List<NdiSourceData> sourceDataList = findLib.findCurrentSources(3000);
            //调用接收
            if (CollectionUtils.isEmpty(sourceDataList)) {
                return;
            }
            for (NdiSourceData sourceData : sourceDataList) {
                if (!sourceData.p_ndi_name.contains("Integrated Webcam")) {
//                    continue;
                }
                ReceiveData receiveData = new ReceiveData(sourceData, new IntByReference(1), new IntByReference(100), true, "MyNdi");
                try (ReveiveLib reveiveLib = new ReveiveLib(receiveData)) {
                    reveiveLib.connect(sourceData);
                    System.out.println("receive ndi_ssource:");
                    System.out.println(sourceData);
                    VideoFrameV2Data p_video_data = new VideoFrameV2Data();
                    AudioFrameV2Data p_audio_data2 = new AudioFrameV2Data();
                    AudioFrameV3Data p_audio_data3 = new AudioFrameV3Data();
                    MetadataFrameData p_metadata = new MetadataFrameData();
                    long startTills = System.currentTimeMillis();
                    do {
                        int receiveFrameType = reveiveLib.recvCaptureV2(p_video_data, p_audio_data2, p_metadata, 5000);
                        switch (receiveFrameType){
                            case 0:
                                //none
                                System.out.println("none");
                                break;
                            case 1:
                                //video
                                System.out.println("video:------------------------------");
                                System.out.println(p_video_data);
                                reveiveLib.freeVideoV2(p_video_data);
                                break;
                            case 2:
                                //audio
                                System.out.println("audio:------------------------------");
                                System.out.println(p_audio_data2);
                                reveiveLib.freeAudioV2(p_audio_data2);
                                break;
                            case 3:
                                //meta data
                                System.out.println("meta:------------------------------");
                                System.out.println(p_metadata);
                                reveiveLib.freeMetadata(p_metadata);
                                break;
                            case 4:
                                System.out.println("error");
                                break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (System.currentTimeMillis() - startTills < 5000);
//                    try (FrameLib frameLib = new FrameLib(reveiveLib.insPoint)) {
//                        do {
//                            frameLib.captureAudio(p_audio_data2, 48000, 2, 0);
//                            System.out.println(p_audio_data2);
//                            Thread.sleep(1000);
//                        } while (System.currentTimeMillis() - startTills < 3000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
    }
}
