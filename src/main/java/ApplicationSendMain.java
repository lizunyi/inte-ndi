import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.weaver.inte.jna.common.data.AudioFrameV2Data;
import com.weaver.inte.jna.common.data.AudioFrameV3Data;
import com.weaver.inte.jna.common.data.MetadataFrameData;
import com.weaver.inte.jna.common.data.NdiSourceData;
import com.weaver.inte.jna.common.data.VideoFrameV2Data;
import com.weaver.inte.jna.find.FindData;
import com.weaver.inte.jna.find.FindLib;
import com.weaver.inte.jna.frame.FrameLib;
import com.weaver.inte.jna.receive.ReceiveData;
import com.weaver.inte.jna.receive.ReveiveLib;
import com.weaver.inte.jna.send.SendData;
import com.weaver.inte.jna.send.SendLib;
import com.weaver.inte.jna.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;


public class ApplicationSendMain {

    public static void main(String[] args) throws IOException {
//        FindData findData = new FindData(true, "Memo", "192.168.1.128");
        FindData findData = new FindData(true, "", "");
        try (FindLib findLib = new FindLib(findData)) {
            //查找NDI源
            List<NdiSourceData> sourceDataList = findLib.findCurrentSources(5000);
            if (CollectionUtils.isEmpty(sourceDataList)) {
                return;
            }
            for (NdiSourceData sourceData : sourceDataList) {
                final NdiSourceData source = sourceData;
                new Thread(() -> {
                    ReceiveData receiveData = new ReceiveData(source, new IntByReference(1), new IntByReference(100), true, "MyNdi");
                    //接收NDI源数据
                    try (ReveiveLib reveiveLib = new ReveiveLib(receiveData)) {
                        reveiveLib.connect(source);
                        System.out.println("receive ndi_source:");
                        System.out.println(source);
                        VideoFrameV2Data p_video_data = new VideoFrameV2Data();
                        AudioFrameV2Data p_audio_data2 = new AudioFrameV2Data();
                        MetadataFrameData p_metadata = new MetadataFrameData();
                        SendData sendData = new SendData(source.p_ndi_name, "HH", true, true);
                        try (SendLib sendLib = new SendLib(sendData)) {
                            MetadataFrameData p_metadata2 = new MetadataFrameData();
                            do {
                                int receiveFrameType = reveiveLib.recvCaptureV2(p_video_data, p_audio_data2, p_metadata, 100);
                                switch (receiveFrameType) {
                                    case 0:
                                        //none
                                        break;
                                    case 1:
                                        //send video
                                        System.out.println("send video:------------------------------");
                                        System.out.println(p_video_data.p_data);
                                        sendLib.sendVideoV2(p_video_data);
                                        reveiveLib.freeVideoV2(p_video_data);
                                        break;
                                    case 2:
                                        //send audio
                                        System.out.println("send audio:------------------------------");
                                        System.out.println(p_audio_data2.p_data);
                                        sendLib.sendAudioV2(p_audio_data2);
                                        reveiveLib.freeAudioV2(p_audio_data2);
                                        break;
                                    case 3:
                                        //send meta
                                        System.out.println("send meta:------------------------------");
                                        System.out.println(p_metadata.p_data);
                                        sendLib.sendMetadata(p_metadata);
                                        p_metadata2.p_data = "<ndi_capabilities web_control=\"http://192.168.1.1/6f073725619ddb4fd88bac7ceba569ca70666f590a6f141653cc1a34b6a54bf2\" ntk_ptz=\"true\" ntk_exposure_v2=\"true\" />";
                                        sendLib.sendAddConnectionMetadata(p_metadata2);
//                                      reveiveLib.freeMetadata(p_metadata);
                                        break;
                                    case 4:
                                        System.out.println("error");
                                        break;
                                }
                            } while (true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}
