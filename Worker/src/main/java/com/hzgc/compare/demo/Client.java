package com.hzgc.compare.demo;

import com.hzgc.compare.rpc.client.RpcClient;
import com.hzgc.compare.rpc.client.result.AllReturn;
import com.hzgc.compare.rpc.protocol.JsonUtil;
import com.hzgc.compare.worker.Service;
import com.hzgc.compare.worker.common.CompareParam;
import com.hzgc.compare.worker.common.Feature;
import com.hzgc.compare.worker.common.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        RpcClient rpcClient = new RpcClient("172.18.18.100:2181,172.18.18.101:2181,172.18.18.102:2181");
        Thread.sleep(3000);
        Service service = rpcClient.createAll(Service.class);
//        service.stopTheWorker();
//        System.exit(0);
        List<String> ipcIds = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            ipcIds.add("" + i);
        }
        float[] featute = new float[]{2.1783385f,-0.56258285f,-0.71289057f,2.24726f,4.403554f,0.110902384f,1.0347606f,2.0991583f,-2.93272f,-0.41105366f,-2.481345f,4.5244136f,0.2295451f,-1.3578924f,-0.91997707f,-0.74590755f,-2.0203838f,1.8630043f,4.1742554f,-0.4187439f,0.7861563f,-2.9255857f,-6.310042f,2.4381006f,-0.7271543f,2.5954685f,-1.5058111f,-2.2482154f,3.5578318f,-3.014266f,0.5050856f,5.5229754f,1.3834182f,1.8123125f,0.6023896f,-5.1417546f,-3.4768968f,-3.1517324f,-2.2133813f,5.4334292f,-0.29713827f,2.1851606f,3.0162475f,-0.4135138f,0.54025483f,-6.142504f,-0.5462427f,-0.90979785f,0.3525049f,-0.90181595f,-0.21953037f,-2.4411843f,0.17255262f,2.4755733f,-3.2960653f,1.3816118f,-0.21211682f,2.1377692f,-0.6739951f,0.12308353f,-0.71643096f,-4.8198333f,-1.8660468f,1.0494642f,1.4420009f,-3.0338485f,0.38101703f,-1.6075554f,-1.648149f,2.6592536f,-0.9293765f,5.398171f,1.5616484f,-2.651761f,1.866477f,0.282629f,-4.4791765f,-4.6636105f,0.9288113f,-2.2756078f,-0.1785117f,-4.2208657f,-2.0367575f,0.74508107f,0.663885f,-2.6769094f,3.0422919f,-3.6987789f,-0.95837915f,-2.0464473f,-1.4722323f,1.4040495f,-2.6097264f,0.2266591f,-0.418723f,1.4927355f,-3.6280448f,1.7731327f,1.6994283f,1.2647266f,-0.9116864f,-6.7210555f,0.4070866f,2.011968f,3.3857305f,-1.7255921f,1.9270663f,-3.008258f,1.0411296f,-0.15479185f,-4.036471f,-0.98960555f,-2.7537806f,-2.030766f,3.3454657f,-4.2839484f,2.2485328f,-1.4458058f,-2.7315378f,-0.47972825f,1.5781431f,0.7650519f,-0.7597458f,3.1917574f,-1.7068652f,1.869457f,-0.043526128f,-1.5801246f,-0.88615113f,-5.2162204f,2.045257f,0.68706316f,4.9143114f,1.2781019f,-1.6269503f,2.1268365f,-2.3694956f,4.5506854f,0.7248566f,-0.5365694f,2.023342f,-2.6163056f,-1.7997112f,-4.3309207f,-4.702115f,0.77838427f,3.38861f,1.4253358f,-1.3309684f,3.8423533f,1.4451888f,-2.058887f,-0.32679045f,-3.8733444f,-6.8521876f,-0.21817818f,-0.3526885f,-0.0980161f,-5.229416f,0.5868207f,1.3304201f,-1.22357f,-5.4186234f,-4.239577f,0.7539134f,1.8937725f,-1.159544f,3.3365684f,-0.4202554f,0.080041155f,-2.7748814f,-3.22233f,-0.37389836f,1.1862513f,-4.107333f,1.2142235f,2.919656f,-1.6870542f,1.4218396f,-2.3510475f,-2.4361165f,0.62139004f,2.2344294f,0.14133997f,3.0309587f,0.7527932f,-0.68407583f,3.8810332f,-4.915336f,-1.9727575f,-2.8991854f,2.6908128f,0.17579669f,-2.460981f,1.6000998f,2.9461505f,2.3005102f,-0.6621325f,2.1884987f,0.8411083f,3.5309095f,0.56215775f,-1.4954597f,1.0832431f,0.3607236f,2.0921504f,-3.1485577f,2.9283903f,4.1183715f,1.1011484f,-1.0374556f,0.11093296f,-1.1958863f,-0.4741954f,2.7860758f,3.3475056f,-1.1146775f,0.49347186f,4.236897f,2.3900695f,-1.4716153f,0.7915506f,-5.187028f,0.8656845f,-0.5202146f,-0.7617568f,-1.5923818f,0.24019918f,-4.157788f,-1.0385476f,-5.177871f,-2.6730237f,-5.169842f,3.713593f,1.4636836f,0.69627494f,-3.9977224f,2.5082276f,2.0134482f,-3.7552373f,1.7338184f,-1.2562239f,0.4435504f,-1.2890854f,3.9126132f,-2.015425f,4.3794603f,-1.8879489f,-1.154665f,-2.818189f,-5.39009f,-2.993009f,3.2344232f,-2.0698855f,4.033552f,2.6666536f,1.89565f,-0.64011294f,4.386598f,4.007972f,-1.6369182f,1.5733618f,1.5105262f,-0.622375f,2.7562885f,2.203555f,0.78815424f,-2.0754297f,1.0771393f,-0.7857554f,-1.3206966f,0.25413173f,-0.858071f,-2.7854176f,1.1745758f,4.6797485f,3.2561212f,2.9364855f,-3.2355294f,0.99981415f,-0.5400294f,-3.2621605f,2.8271837f,0.17166986f,1.0837682f,-0.051099926f,-3.3232534f,4.0721154f,-4.5891957f,-0.8734094f,1.6391941f,1.3374312f,1.7503392f,1.002142f,-0.8000257f,0.542537f,1.9464846f,-1.2554941f,2.598982f,-0.40726513f,3.8535295f,3.317849f,-1.5744793f,0.98601663f,0.3874668f,-0.65336686f,0.49091178f,0.8788434f,-1.7886405f,0.21290085f,-2.3850257f,-2.9583175f,1.4220681f,8.059087f,-3.295f,0.29926443f,2.9660287f,-1.3972027f,-0.20794502f,-1.4636691f,0.0395644f,-1.3227196f,2.9376252f,-1.6020133f,1.5251149f,1.8420342f,-2.0611322f,-1.5663915f,0.35977492f,-2.6874776f,-0.9842238f,0.4135111f,2.1926117f,1.0863873f,0.030417532f,-2.9539893f,1.9119471f,-1.6611432f,2.0661206f,-2.6776555f,-1.0807745f,0.9376882f,2.9140787f,-0.8421319f,-1.8535026f,-1.3084409f,1.2544512f,1.0918163f,1.0982618f,-3.4379842f,-3.5423832f,2.4675353f,4.6932797f,0.3112768f,-0.11639554f,1.384973f,-3.1243677f,-2.2524927f,-1.2863703f,-2.7521956f,0.31203166f,1.368658f,5.9206247f,2.2883604f,2.5791664f,0.033218905f,0.5589295f,-1.6646559f,3.3137436f,1.4896197f,-0.5456659f,-1.1581081f,-2.9009469f,1.7229913f,-0.5523995f,1.6988112f,-1.1000215f,-5.1803026f,-0.07361034f,-0.56486607f,-0.44478053f,-1.7708501f,2.7986338f,-4.959197f,-2.6944766f,1.6910777f,1.8053848f,6.980475f,-0.6674685f,-3.6797826f,-3.1262934f,2.701442f,3.8404431f,5.4122753f,-2.26715f,-1.4682174f,0.7443377f,1.5230501f,3.4442174f,1.0581878f,5.479876f,1.5043132f,2.923884f,-2.7532551f,-2.9702854f,-1.7629969f,2.8098428f,-4.223606f,2.752785f,2.5838122f,-0.6909586f,7.7622905f,-6.319632f,-2.9423273f,1.474857f,4.4979434f,1.3888375f,0.26938653f,2.5920992f,0.7763644f,-7.6346836f,-0.38895988f,-2.42978f,-6.196437f,-1.443558f,-0.34054273f,2.0048013f,-4.2496324f,-1.7951952f,3.2421253f,2.2421296f,-2.8338094f,-3.0324342f,-3.5852907f,4.3211136f,0.124060124f,-2.6200724f,4.2853746f,3.2778277f,3.6433542f,-2.0828824f,-2.1474338f,-2.6268394f,4.741613f,-0.31533098f,3.1939793f,1.6228994f,-0.22683874f,-0.24827878f,-1.0983026f,3.7833395f,-2.0967157f,0.33130673f,-6.1172895f,-0.43289462f,2.990085f,-0.36317545f,-3.0382915f,3.725006f,3.2091115f,6.0882196f,5.3985243f,-2.513191f,4.3729715f,4.32874f,0.053454548f,3.5088696f,-2.346345f,-1.6272867f,4.622449f,-2.1527264f,-0.42283976f,-4.3947415f,2.2828937f,3.1903963f,1.5338303f,-1.8867623f,2.8624887f,-0.32001096f,-1.1286557f,-1.8435216f,5.8151655f,2.5669081f,-0.9849613f,4.828448f,1.8040355f,1.8334017f,-2.594624f,-4.6285057f,2.0718284f,-5.629434f,2.025448f,-0.14701918f,-0.16804303f,3.2992117f,3.763987f,-0.12956077f,-2.984935f,-1.686731f,5.829381f,-0.95622647f,-0.29533544f,0.8083263f,3.0074499f,-1.4867336f,-2.3448238f,-5.4782867f,-3.5838683f,-5.6466365f,-2.4822252f,-3.2735853f,1.6662846f};
        long star = System.currentTimeMillis();
        List<Feature> features = new ArrayList<>();
        features.add(new Feature(null, featute));
//        CompareParam param = new CompareParam(new ArrayList<String>(), null,"2018-07-16", "2018-07-18", features, 0.8f, 25, true);
//        AllReturn<SearchResult> resultAllReturn = service.retrievalOnePerson(param);
//        System.out.println("++++++++++++++++++++++++++++++++++");
//        System.out.println("compare1 "+ (System.currentTimeMillis() - star));
//        Thread.sleep(1000 * 20);

//        long compare1 = System.currentTimeMillis();
//        CompareParam param2 = new CompareParam(ipcIds, null,"2018-07-16", "2018-07-18", features, 0.8f, 25, true);
//        AllReturn<SearchResult> resultAllReturn2 = service.retrievalOnePerson(param2);
//        System.out.println("compare2 "+ (System.currentTimeMillis() - compare1));
//        Thread.sleep(1000 * 20);

        long compare2 = System.currentTimeMillis();
        CompareParam param3 = new CompareParam(ipcIds, null,"2018-07-18", "2018-07-20", features, 0.9f, 25, true);
        AllReturn<SearchResult> resultAllReturn3 = service.retrievalOnePerson(param3);
        System.out.println("compare3 "+ (System.currentTimeMillis() - compare2));
        System.out.println(JsonUtil.objectToJson(resultAllReturn3.getResult()));
    }
}
