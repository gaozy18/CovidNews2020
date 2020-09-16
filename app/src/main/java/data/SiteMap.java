package data;

import java.util.HashMap;

public class SiteMap {
    public HashMap<String, String> countryMap;
    public HashMap<String, String> stateMap;
    SiteMap () {
        countryMap = new HashMap<String, String>();
        stateMap = new HashMap<String, String>();
        String[] en_countries = {"Afghanistan", "Akrotiri", "Albania", "Algeria", "American Samoa",
                "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barb.", "Argentina", "Armenia",
                "Aruba", "Ashmore and Cartier Is.", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain",
                "Baikonur", "Bajo Nuevo Bank", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin",
                "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herz.", "Botswana", "Br. Indian Ocean Ter.", "Brazil",
                "British Virgin Is.", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia",
                "Cameroon", "Canada", "Cayman Is.", "Central African Rep.", "Chad", "Chile", "China", "Clipperton I.",
                "Colombia", "Comoros", "Congo", "Cook Is.", "Coral Sea Is.", "Costa Rica", "Croatia", "Cuba",
                "Curaçao", "Cyprus", "Cyprus U.N. Buffer Zone", "Czechia", "Côte d'Ivoire", "Dem. Rep. Congo",
                "Denmark", "Dhekelia", "Djibouti", "Dominica", "Dominican Rep.", "Ecuador", "Egypt", "El Salvador",
                "Eq. Guinea", "Eritrea", "Estonia", "Ethiopia", "Faeroe Is.", "Falkland Is.", "Fiji", "Finland",
                "Fr. Polynesia", "Fr. S. Antarctic Lands", "France", "Gabon", "Gambia", "Georgia", "Germany",
                "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guam", "Guatemala", "Guernsey", "Guinea",
                "Guinea-Bissau", "Guyana", "Haiti", "Heard I. and McDonald Is.", "Honduras", "Hungary", "Iceland",
                "India", "Indian Ocean Ter.", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy",
                "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait",
                "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania",
                "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
                "Marshall Is.", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
                "Montenegro", "Montserrat", "Morocco", "Mozambique", "Myanmar", "N. Cyprus", "N. Mariana Is.",
                "Namibia", "Nauru", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger",
                "Nigeria", "Niue", "Norfolk Island", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine",
                "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Is.", "Poland", "Portugal",
                "Puerto Rico", "Qatar", "Romania", "Russia", "Rwanda", "S. Geo. and the Is.", "S. Sudan", "Saint Helena",
                "Saint Lucia", "Samoa", "San Marino", "Saudi Arabia", "Scarborough Reef", "Senegal", "Serbia",
                "Serranilla Bank", "Seychelles", "Siachen Glacier", "Sierra Leone", "Singapore", "Sint Maarten",
                "Slovakia", "Slovenia", "Solomon Is.", "Somalia", "Somaliland", "South Africa", "South Korea",
                "Spain", "Sri Lanka", "St-Barthélemy", "St-Martin", "St. Kitts and Nevis", "St. Pierre and Miquelon",
                "St. Vin. and Gren.", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "São Tomé and Principe",
                "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago",
                "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Is.", "Tuvalu", "U.S. Minor Outlying Is.",
                "U.S. Virgin Is.", "USNB Guantanamo Bay", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom",
                "United States of America", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican", "Venezuela", "Vietnam",
                "W. Sahara", "Wallis and Futuna Is.", "World", "Yemen", "Zambia", "Zimbabwe", "eSwatini", "Åland"};
        String[] zh_countries = {"阿富汗", "Akrotiri", "阿尔巴尼亚", "阿尔及利亚", "美属萨摩亚", "安道尔",
                "安哥拉", "安圭拉", "南极洲", "安提瓜和巴布达", "阿根廷", "亚美尼亚", "阿鲁巴", "阿什莫尔和卡捷岛", "澳大利亚",
                "奥地利", "阿塞拜疆", "巴哈马", "巴林", "拜科努尔", "巴霍努埃沃礁", "孟加拉国", "巴巴多斯", "白罗斯",
                "比利时", "伯利兹", "贝宁", "百慕大", "不丹", "玻利维亚", "波斯尼亚和黑塞哥维那", "波札那", "英属印度洋领地",
                "巴西", "英属维尔京群岛", "文莱", "保加利亚", "布吉纳法索", "蒲隆地", "佛得角", "柬埔寨", "喀麦隆", "加拿大",
                "开曼群岛", "中非共和国", "乍得", "智利", "中华人民共和国", "克利珀顿岛", "哥伦比亚", "葛摩", "刚果共和国",
                "库克群岛", "珊瑚海群岛", "哥斯达黎加", "克罗地亚", "古巴", "库拉索", "赛普勒斯", "赛普勒斯联合国缓冲区", "捷克",
                "科特迪瓦", "刚果民主共和国", "丹麦", "Dhekelia", "吉布提", "多米尼克", "多明尼加", "厄瓜多尔", "埃及",
                "萨尔瓦多", "赤道几内亚", "厄立特里亚", "爱沙尼亚", "埃塞俄比亚", "法罗群岛", "福克兰群岛", "斐济", "芬兰",
                "法属玻里尼西亚", "法属南部领地", "法国", "加蓬", "冈比亚", "格鲁吉亚", "德国", "迦纳", "直布罗陀", "希腊",
                "格陵兰", "格林纳达", "关岛", "危地马拉", "根西岛", "几内亚", "几内亚比索", "圭亚那", "海地", "赫德岛和麦克唐纳群岛",
                "洪都拉斯", "匈牙利", "冰岛", "印度", "澳属印度洋领地", "印度尼西亚", "伊朗", "伊拉克", "爱尔兰共和国", "马恩岛",
                "以色列", "意大利", "牙买加", "日本", "泽西岛", "约旦", "哈萨克斯坦", "肯尼亚", "吉里巴斯", "科索沃", "科威特",
                "吉尔吉斯斯坦", "老挝", "拉脱维亚", "黎巴嫩", "莱索托", "利比里亚", "利比亚", "列支敦斯登", "立陶宛", "卢森堡",
                "马其顿共和国", "马达加斯加", "马拉维", "马来西亚", "马尔代夫", "马里共和国", "马耳他", "马绍尔群岛", "毛里塔尼亚",
                "毛里求斯", "墨西哥", "密克罗尼西亚联邦", "摩尔多瓦", "摩纳哥", "蒙古国", "蒙特内哥罗", "蒙塞拉特岛", "摩洛哥",
                "莫桑比克", "缅甸", "北赛普勒斯土耳其共和国", "北马里亚纳群岛", "纳米比亚", "诺鲁", "尼泊尔", "荷兰", "新喀里多尼亚",
                "新西兰", "尼加拉瓜", "尼日尔", "奈及利亚", "纽埃", "诺福克岛", "朝鲜民主主义人民共和国", "挪威", "阿曼", "巴基斯坦",
                "帛琉", "巴勒斯坦", "巴拿马", "巴布亚新几内亚", "巴拉圭", "秘鲁", "菲律宾", "皮特凯恩群岛", "波兰", "葡萄牙",
                "波多黎各", "卡塔尔", "罗马尼亚", "俄罗斯", "卢旺达", "南乔治亚岛与南桑威奇群岛", "南苏丹", "圣赫勒拿岛", "圣卢西亚",
                "萨摩亚", "圣马力诺", "沙特阿拉伯", "黄岩岛", "塞内加尔", "塞尔维亚", "塞拉纳浅滩", "塞舌尔", "锡亚琴冰川",
                "塞拉利昂", "新加坡", "荷属圣马丁", "斯洛伐克", "斯洛文尼亚", "索罗门群岛", "索马里", "索马里兰", "南非", "大韩民国",
                "西班牙", "斯里兰卡", "圣巴泰勒米岛", "法属圣马丁", "圣克里斯多福与尼维斯", "圣皮埃尔和密克隆群岛",
                "圣文森特和格林纳丁斯", "苏丹共和国", "苏利南", "瑞典", "瑞士", "叙利亚", "圣多美和普林西比", "塔吉克斯坦",
                "坦桑尼亚", "泰国", "东帝汶", "多哥", "东加", "千里达及托巴哥", "突尼西亚", "土耳其", "土库曼斯坦",
                "特克斯和凯科斯群岛", "吐瓦鲁", "美国本土外小岛屿", "美属维尔京群岛", "USNB Guantanamo Bay", "乌干达", "乌克兰",
                "阿拉伯联合酋长国", "英国", "美国", "乌拉圭", "乌兹别克斯坦", "万那杜", "梵蒂冈", "委内瑞拉", "越南", "西撒哈拉",
                "瓦利斯和富图纳", "全球", "也门", "赞比亚", "辛巴威", "斯威士兰", "奥兰群岛"};
        String[] en_state = {"Anhui", "Beijing", "Chongqing", "Fujian", "Gansu", "Guangdong", "Guangxi", "Guizhou",
                "Hainan", "Hebei", "Heilongjiang", "Henan", "Hong Kong", "Hubei", "Hunan", "Inner Mongol", "Jiangsu",
                "Jiangxi", "Jilin", "Liaoning", "Macao", "Ningxia", "Qinghai", "Shaanxi", "Shandong", "Shanghai",
                "Shanxi", "Sichuan", "Taiwan", "Tianjin", "Xinjiang", "Xizang", "Yunnan", "Zhejiang"};
        String[] zh_state = {"安徽省", "北京市", "重庆市", "福建省", "甘肃省", "广东省", "广西壮族自治区", "贵州省",
                "海南省", "河北省", "黑龙江省", "河南省", "香港", "湖北省", "湖南省", "内蒙古自治区", "江苏省", "江西省",
                "吉林省", "辽宁省", "澳门", "宁夏回族自治区", "青海省", "陕西省", "山东省", "上海市", "山西省", "四川省",
                "台湾省", "天津市", "新疆维吾尔自治区", "西藏自治区", "云南省", "浙江省"};
        for (int i=0; i<=en_countries.length-1; i++) {
            countryMap.put(zh_countries[i], en_countries[i]);
        }
        for (int i=0; i<=en_state.length-1; i++) {
            stateMap.put(zh_state[i], en_state[i]);
        }
    }
}
