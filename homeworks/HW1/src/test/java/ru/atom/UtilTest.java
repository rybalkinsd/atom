package ru.atom;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


public class UtilTest {

    
    @Test
    public void max0() throws Exception {
        assertThat(Util.max(new int[]{42}), is(equalTo(42)));
    }
    
    @Test
    public void max1() throws Exception {
        assertThat(Util.max(new int[]{1, 2, 3}), is(equalTo(3)));
    }
    
    @Test
    public void max2() throws Exception {
        assertThat(Util.max(new int[]{2, 2, 0}), is(equalTo(2)));
    }
    
    @Test
    public void max3() throws Exception {
        int[] values = {3262, 3989, 4825, 3751, 147, 2376, 2866, 1192, 2259, 905, 4810, 2481, 4243,
            3907, 2958, 4061, 2410, 2253, 273, 3932, 4479, 4771, 1858, 3329, 3427, 3341, 3890, 3283,
            1469, 2793, 292, 2371, 772, 1207, 2101, 985, 4615, 2290, 1147, 1659, 4000, 2958, 2010,
            876, 2272, 242, 185, 3452, 2132, 4820, 3756, 577, 740, 731, 4066, 2666, 2316, 1445,
            1430, 892, 2100, 2981, 2883, 2331, 3010, 2936, 4520, 727, 4727, 2501, 1619, 592, 1791,
            3590, 122, 2599, 4450, 3256, 2148, 4306, 4448, 995, 1894, 1956, 4609, 4462, 1282, 4103,
            2785, 1890, 2834, 3262, 2843, 761, 4610, 4220, 2685, 2155, 3379, 1555, 4521, 1429, 916,
            4562, 203, 3502, 395, 2470, 3910, 1271, 4650, 3059, 4114, 3071, 2676, 1527, 264, 2008,
            2070, 1843, 1158, 1042, 2314, 3119, 2709, 4857, 4439, 737, 663, 1381, 1733, 4770, 3399,
            4902, 1644, 869, 2833, 2698, 4180, 1716, 952, 2885, 3558, 3657, 902, 1204, 346, 1431,
            1102, 14, 4650, 2418, 221, 4575, 1052, 4913, 3671, 4730, 973, 3134, 1070, 1682, 4144,
            3416, 2494, 3757, 341, 2822, 4187, 2021, 3823, 3751, 2500, 2109, 4262, 4282, 729, 2893,
            683, 4487, 1150, 2162, 194, 1825, 1100, 1165, 617, 3033, 3589, 966, 2071, 2616, 2958,
            997, 3760, 3312, 4577, 988, 3325, 472, 367, 4108};
        assertThat(Util.max(values), is(equalTo(4913)));
    }
    
    @Test
    public void sum0() throws Exception {
        assertThat(Util.sum(new int[] {42}), is(equalTo(42L)));
    }
    
    @Test
    public void sum1() throws Exception {
        assertThat(Util.sum(new int[]{1, -2, 3}), is(equalTo(2L)));
    }
    
    @Test
    public void sum2() throws Exception {
        assertThat(Util.sum(new int[]{-1, 0, -1}), is(equalTo(-2L)));
    }
    
    @Test
    public void sum3() throws Exception {

        int[] values = {-1474724389, 682594446, -1908752577, -1529308579, 2077283217, 2082453684,
            -1393301062, 770907957, -1236598274, 2095271857, -740944851, 690100578, 1205967523,
            1882042738, -373000365, -202439561, -1040081538, 1588841659, 685998530, 2019352469,
            -1800807521, 142414883, 1321799336, -2004537327, -942379270, -404489374, -957557834,
            -399504812, -1822467201, -1228241713, -2032321957, -563526397, -994726553, 1984668451,
            -884205515, -996984225, -2084853551, -1367706750, -28706108, 899267565, 93707916,
            1967952828, -1321338272, 888020056, 1829517878, 1239226575, -611104660, -65047658,
            717708677, 940173744, 150269132, -1787788982, -457712412, 1346732618, 310601363,
            603902313, 1490825537, -728538426, -1661524991, -1121646817, -40668379, -1639600371,
            1298007468, 754741747, -839372028, 74891914, 1448173820, 2004898089, -861928746,
            -912147404, 996488667, -1827182233, 936477926, 1802061515, -144143443, -831598438,
            688657385, 986456417, -820369026, -858057702, -1277601711, 1224420379, 880024954,
            -1608311541, 1855086307, -570622664, 434358177, -2031454458, 1481049222, -2037956014,
            -940876870, 1646714105, 311413598, -29986332, -1106964856, 1416048715, -2113966823,
            1188073740, -529571269, -512737725, -848642424, 348656017, 1661814313, -1642727510,
            -393037711, -1728050138, -1392771651, 1165885102, -215631453, 1478712047, 875299503,
            878882568, -1496689079, -1259055035, -613853579, -1698954143, 1463240835, -1659681354,
            856880889, -1968831759, -75409870, 857055055, -1802999157, -1364031895, -1056882669,
            -1381441764, 357537560, 1280219923, -2084499953, 102724847, -530294628, 381797686,
            -2021569328, -1854998944, 1967557332, -1362109762, 341577879, -1718433003, 1685223992,
            -798207204, 1679085900, 1656150339, 1530589224, -1653067413, 646018006, 1539438196,
            482037349, 141683923, -311814996, 890249354, -2046071358, -1634665678, 157612, 
            648594179,
            138818586, 1010622968, 1962459435, 1094552702, 1720653188, 459927890, 1564744780,
            1862441085,
            -1068370747, 392433034, 849064597, 54063467, 1941706502, -2026755411, 1746401471, 
            -581914911,
            -1725686074, 625172077, -1020914484, 924333092, 1493320209, -1161778103, 871637343,
            -121482510, 981020657, -1393758230, 905799, -1444587907, -2088543963, -1112634928,
            -851030436, -667862345, -1797287920, -738938323, 528831118, -1547988016, -1106037772,
            526685713, 651883066, 1638132220, -1199088801, 1926800070, -576812865, 1220251001,
            -550915611, -184638595, -711806895, 2097686224, 1498479886, -603482839, 676418060,
            -1364687014, 120618130, -1960422230, -1746918580, -1021870587, -1155229245, 885558062,
            1769940027, -1925139368, -1864983990, -582517548, 466088875, -1650993228, 1490831893,
            -444293743, 323882021, 1604489947, -1605119856, -306180009, 1398619713, -2106975306,
            -682941166, 355824755, -2072708041, -2058266286, 835049113, 753771830, -319534410,
            -283580885, -1121248582, 435834839, -1286378764, 1906995885, -1575921710, 926581655,
            830837253, 249121783, -134504831, 1192599590, 1341014384, 1088293601, -252656039,
            1418479173, -1594157825, -2127581376, 1830494673, -2032849236, 908859863, 431795048,
            1713044863, 1303776045, 560071367, -1951751182, -1247453562, -1121528296, 451595748,
            -1679038530, -693390260, 1590703073, 2067443541, 493216827, -1428131472, 98290432,
            1472746858,
            978590181, -2093498373, 1201124888, 710990323, 1866945807, -1740454700, 2145154139,
            -1699154737, -908513566, 1781999225, -1120469972, 1078346690, -1205331291, -1084503316,
            2093929477, 130333580, -1129282754, -173080342, -887466598, 830051894, 1616624887,
            1495894114,
            -1695449282, 210129582, 2061881221, 507850277, 45217670, -306996842, 1548180550,
            1738862297,
            -2060638421, -1102767272, 148972746, 2032653619, -1270641356, -1897612419, -637864555,
            2039416676, -744574636, 482647359, -1258292959, 695384019, -852666224, 1090315612,
            972994345,
            -104272627, -853811164, -48020180, -711769052, 1492931833, 1648157351, -1050172683,
            1606837519, -1027565744, -1737192595, -1318947970, -1194022033, -1347101877, 1593523244,
            -92890911, -1032008298, 1975284893, -1032689439, -1166943579, -928727260, 1006693283,
            1292946932, -868988148, 1259949520, -2062560039, -606509926, -203936344, -1943779736,
            1639590800, -1457342086, 156840756, -118397418, -142466083, 1491765650, -1319952736,
            -1585800220, -1329616940, -993277604, 207431533, 2050514561, -1435307018, -903120340,
            940092731, 39524933, -146798873, -946671141, 232722591, 1646197618, 1065670755,
            -59475798,
            -702298822, -434399044, 2133298035, -83795617, -1831014138, 1787118090, -237432869, 
            -1937309772,
            -1661588237, 1550710654, 693888726, 809115889, -562677070, 997215411, 1176895236,
            11463383,
            1794753487, 1276034117, 78633891, -1212857152, 1345484293, -2034880619, -399344899, 
            -1993330941,
            -2075455283, -654655795, 297316385, 369142941, -49924040, -493753055, 260039637,
            511910669,
            1903632638, 1442702680, 535785674, 1520981118, -1634702891, -1307477366, 1949110033, 
            -761178069,
            1035384327, -844304085, 331875021, -1062718210, 692706622, -1768958905, 878445098,
            -790297990,
            1995490466, -551789809, 1890059917, 168408027, -140430043, 1504518803, 161774861, 
            -1237520004,
            -2095168739, 585514960, -1259894240, -628185993, 2101156626, -76091923, -715303783, 
            -1581836142,
            -135706663, -1924047679, -247100463, 797694041, -488716831, -1800248424, -641295068,
            -1683773562,
            -1566476340, 1252727863, 142232735, -178962519, -1063333823, 159033884, -312564489,
            -1210928380,
            -673200897, 1663480329, 785478332, -1528981065, 1769889590, 480593347, -1796489523, 
            -1515295460,
            1936948497, 198395927, -1795818482, -966019724, -528050025, 1352568093, 939842648,
            926150082,
            -1435326541, -361022784, -1537605455, 814170615, -1661965936, 649080189, -1826424644,
            -972256434, 1921496362, -1166121622, -689683086, -595644679, -1207001822, -1176256647,
            562385682, 1145684733, -891913007, -1325826307, -20079018, 875273561, -51101026, 
            1727228781,
            2020767975, 881176942, -1542288492, -1228110232, -958914287, -825401446, -1760467922,
            788176377, 1956651194, -1150674139, 2124464757, 257048347, 1313482640, -1389557152,
            -901771956, -958870401, -1446259583, 2061501707, 383665418, 1255849356, 123150960,
            660313335, 872128841, -780645978, -1144099804, -311829268, -1595492355, 1950024626,
            1776302980, -390690356, -1833139769, -1754584614, 666130433, -2039860745, 645123979,
            1090760077, -1615411118, -1995323663, -172882625, -1433389111, -1968915609, -229049022,
            -1833947642, -1406095972, 181046423, -1943482723, -2074821251, 1219623143, -1549707493,
            1881766468, -652766637, -882632347, 1760414911, -759584854, -1031074273, -61750805,
            610878422,
            1667403609, 1841190509, -553777680, 1679372397, -756330986, 1398412057, -284107952, 
            -452428604, -324286910, -157663776, -1784449972, -1856408327, 1071756162, -1050880205, 
            -1654016555, 886979078, -1932471134, -1230597042, 1007046153, 1003548653,
            580997046, -1048847735, 531312381, -752608463, -1803701118, -1258447890, 436236733,
            -596188792, -928759525, -1180767990, 141026704, -1874845235, 1765100749, 188828680,
            -1907110016, -897673413, 1455619391, 307446011, -322200040, -1200915496, 1984211413,
            401432442, -1834904209, -2087690706, 1095930197, -6289039, 126400357, 796401216, 
            -155925044, -1972587722, 829058092, -432126532, 1687843506, -92071718, 189771530, 
            -1799409812, -1552096948, 1611396289, 539607900, -597773838, -83632303, 433064821, 
            637711837, -578666060, -1216910384, -232546122, 534654504, 1043774996, 950324816, 
            -697587243, 1779913851, -1823908380, -1462010824, 655577409, -24064067, 715026201, 
            -435654543, -1944215710, 1339563969, 1258806718, 1849481545, -1326949211, 346937292, 
            -1417103830, -296271718, 896696535, -998736556, -116255116, 592745003, 97108919, 
            -515895542, 1100563239, 2000725077, 1986536133, 1227651098, 2100155186, 1746151145,
            1736667232, -127831119, -592304350, -1018684335, 115228687, -122248001, 558342034,
            -490389260, -523582434, -814553602, -626833695, -782760737, -217747348, -434235855,
            -862526939, -466093151, -1743358333, 317514929, -530211488, 1723590261, -179933463,
            729702949, 1547355889, 1262599334, 958645173, 433805712, -980660659, -219916753, 
            1650176100, 740537744, -375784978, 1796119713, 2139286253, -1151961629, 1423280267,
            -2036963767, -1114158574, 23394606, -972082808, -435724399, 499449080, 1116380921,
            1476737199, -362240346, 277817337, -974256642, -1699968736, 625868123, 1445264057,
            2116119202, 1081314182, -1162889908, -1901489289, 2003560899, 1780460427, 1393736262,
            -1698282587, -266490956, -1057406835, 1362910950, -498857543, 1926052741, 704695697,
            -2075930753, 1960989765, -209940857, -651965321, 797790017, -1100062139, 596148238,
            1209432175, 207201174, -1015032649, 1160627576, 618351531, -1906945585, -176929423,
            1520082542, -1218960298, -868117761, 462223150, -1103837847, -1092336444, -1317068249,
            1638855401, -433101704, 263824106, -1414969197, -1267259794, 889524799, -1478018289,
            -1577333629, 1666799661, 1864768322, -485926461, 1378657804, -711326640, 1818991459,
            1860702480, -115302848, -1822394608, 1441016745, -1469362961, -1540866932, 706721860,
            -1365825470, -883475715, 2069079649, -1872094150, 1143460555, -2026178194, 2100450583,
            -1171485238, 856806605, -198786751, 1513997837, 2022434215, 1842144906, -648151115, 
            -1602451180, -1989156104, -1229087238, -1030941957, 417157389, -1217619673, 112568848,
            1951505964, -576722224, 74737399, -1121583551, 442476668, 907820623, 1305070300,
            1212789318, -1484981328, -157958661, 2001315974, -893871585, -1523616307, -187963291,
            -1667509314, 2101140059, 1704083985, -371277784, -1050610570, -2018050922, -1466880729,
            -769106425, 189929498, -292534205, 1040746645, -501546671, 1538753580, 603153323, 
            -1556393608, 2118468236, -1780376049, 956268853, -1560768909, 848579666, -1884816350, 
            -2085970713, 1743001829, 1946566261, 1822422032, -2041868756, 1994802521, 1987911529, 
            1845312101, -1240665641, -1610014910, -1601896477, 1428449125, 1645621352, 88157159,
            518403261, -1356827368, -163044211, -66393956, -469316836, 1755941545, 1715414506, 
            1470462002, 2041354964, 645703920, 2391068, -649710672, -1729914102, 564726861,
            -956989957, 1361580610, -2115871462, -596551753, -779689697, -770368399, -1224683077,
            1739316500, -621871707, 406281876, -1737245599, 862885578, 174721587, 2128744069,
            -1665042206, 1837672085, 410489912, 517658776, -1710557348, -1240175205, -48187272, 
            -1430878263, 9310805, -1014328899, -1538596921, -2007270092, -1519082718, 1299153967, 
            687014932, 1626319561, 274069645, -197501622, 2070528425, -1029765825, 1275499441, 
            -1481825888, 1231599792, -2063425207, 1319915305, 1606665296, 1969095416, -390074361,
            -741649303, -1998554468, 1557316089, 410701503, 1331856235, -818858824, -660731627,
            -341025332, -1452003187, -1733079068, 82159648, -291777835, 264395961, 1710740924, 
            -16606686, -1932150206, 508808134, 18603214, 1819745371, 1347509228, 1221172509, 
            764272237, -1803505274, -298109165, 350440095, -613280316, 1840289121, -27866732, 
            -1006030394, 1946500141, 1318675565, -631695781, 1284770570, -92391741, 206109682, 
            314613525, 
            1846145925, -1679521483, -1758341562, -305740502, -1812992807, 425370077,
            2006411085, 2119288298, -1688668335, -927444208, -1983562429, 2072888838, 1179265606,
            -666694217, -1332680264, 1095147585, -1214419261, 152482078, -836569380, 1552116582, 
            545439851, 967509004, -1610388228, 176155516, 1616517275, 1007627026, 1344973900, 
            -651206088, -1499568181, -2093633496, -509632794, -1690864974, -1086570183, -180293771,
            -1875343598, -2063004581, 171252936, 2137587053, -928780856, 682135854, -770026716, 
            269572641, 1278440818, 823503654, -1074186198, 506856913, 725259509, -128547601,
            822343559, 2008861382, -1922072846, 322001376, -1193732194, 1538314334, 1373419848,
            -1629843161, -855259473, -415511704, -1143592739, -561194939, -2122492364,
            630284209, 5019038, -1671171907, 344098958, 154568415, 856091331, -785767161, 
            1629852897,
            -1319628676, -302467241, -1923901237, -465304261, 1837141845, -94260489, -1453201333,
            -282733829, 1445939205, 735726297, -929182944, -1364892304, 1054274764, -1389528304,
            230403624, 2089178816, -2031848796, -434079731, 282818750, 1283007243, -2133152874,
            1414327400, 815984940, 495461839, -107851191, -1472540960, -264932259, -41121395};

        assertThat(Util.sum(values), is(equalTo(-53_391_415_378L)));
    }
}