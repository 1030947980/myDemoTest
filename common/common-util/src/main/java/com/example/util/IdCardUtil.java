package com.example.util;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016.08.17.
 * 身份证工具类
 */
public class IdCardUtil {
    //二级维度的性别
    public static String level_sex_1="1";
    public static String level_sex_2="2";
    public static String level_sex_3="3";
    public static String level_sex_1_name="男";
    public static String level_sex_2_name="女";
    public static String level_sex_3_name="未知";




    /**
     * 根据身份证的号码算出当前身份证持有者的年龄
     *
     * @param
     * @throws Exception
     */
    public static int getAgeForIdcard(String idcard) {
        try {
            int age = 0;

            if (StringUtils.isEmpty(idcard)) {
                return age;
            }

            String birth = "";

            if (idcard.length() == 18) {
                birth = idcard.substring(6, 14);
            } else if (idcard.length() == 15) {
                birth = "19" + idcard.substring(6, 12);
            }

            int year = Integer.valueOf(birth.substring(0, 4));
            int month = Integer.valueOf(birth.substring(4, 6));
            int day = Integer.valueOf(birth.substring(6));
            Calendar cal = Calendar.getInstance();
            age = cal.get(Calendar.YEAR) - year;
            //周岁计算
            if (cal.get(Calendar.MONTH) < (month - 1) || (cal.get(Calendar.MONTH) == (month - 1) && cal.get(Calendar.DATE) < day)) {
                age--;
            }

            return age;
        } catch (Exception e) {

        }
        return -1;
    }

    /**
     * 身份证提取出身日期
     *
     * @param card
     * @return
     * @throws Exception
     */
    public static Date getBirthdayForIdcard(String card)
            throws Exception {
        Date b = null;
        if (card.length() == 18) {
            String year = card.substring(6).substring(0, 4);// 得到年份
            String yue = card.substring(10).substring(0, 2);// 得到月份
            String ri = card.substring(12).substring(0, 2);// 得到日
            // String day=CardCode.substring(12).substring(0,2);//得到日
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            b = format.parse(year + "-" + yue + "-" + ri);
        } else if (card.length() == 15) {
            String uyear = "19" + card.substring(6, 8);// 年份
            String uyue = card.substring(8, 10);// 月份
            String uri = card.substring(10, 12);// 得到日
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            b = format.parse(uyear + "-" + uyue + "-" + uri);
        }
        return b;
    }

//    public static
//    if (cardId.length() == 15 || cardId.length() == 18) {
//        if (!this.cardCodeVerifySimple(cardId)) {
//            error.put("cardId", "15位或18位身份证号码不正确");
//        } else {
//            if (cardId.length() == 18 && !this.cardCodeVerify(cardId)) {
//                error.put("cardId", "18位身份证号码不符合国家规范");
//            }
//        }
//    } else {
//        error.put("cardId", "身份证号码长度必须等于15或18位");
//    }

    public static boolean cardCodeVerifySimple(String cardcode) {
        //第一代身份证正则表达式(15位)
        String isIDCard1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        //第二代身份证正则表达式(18位)
        String isIDCard2 ="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";

        //验证身份证
        if (cardcode.matches(isIDCard1) || cardcode.matches(isIDCard2)) {
            return true;
        }
        return false;
    }

    /**
     * 根据身份证的号码算出当前身份证持有者的性别
     * 1 男 2 女 3未知
     *
     * @return
     * @throws Exception
     */
    public static String getSexForIdcard_new(String CardCode){
        String sex = level_sex_3;
        try {
            if (CardCode.length() == 18) {
                if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                    // modifid by lyr 2016-09-29
                    sex = level_sex_2;
                    // modifid by lyr 2016-09-29
                } else {
                    // modifid by lyr 2016-09-29
                    sex = level_sex_1;
                    // modifid by lyr 2016-09-29
                }
            } else if (CardCode.length() == 15) {
                String usex = CardCode.substring(14, 15);// 用户的性别
                if (Integer.parseInt(usex) % 2 == 0) {
                    sex = level_sex_2;
                } else {
                    sex = level_sex_1;
                }
            }
            return sex;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sex;
    }

    public static String getSexForIdcard_new(String CardCode,Integer sexInt){
        if(sexInt != null){
            return sexInt+"";
        }
        return getSexForIdcard_new(CardCode);
    }


    /**
     * 根据身份证的号码算出当前身份证持有者的性别
     * 1 男 2 女 3未知
     *
     * @return
     * @throws Exception
     */
    public static String getSexForIdcard(String CardCode)
            throws Exception {
        if(StringUtils.isEmpty(CardCode)){
            return level_sex_3_name;
        }
        String sex = level_sex_3_name;
        try {
            if (CardCode.length() == 18) {
                if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                    // modifid by lyr 2016-09-29
                    sex = level_sex_2_name;
                    // modifid by lyr 2016-09-29
                } else {
                    // modifid by lyr 2016-09-29
                    sex = level_sex_1_name;
                    // modifid by lyr 2016-09-29
                }
            } else if (CardCode.length() == 15) {
                String usex = CardCode.substring(14, 15);// 用户的性别
                if (Integer.parseInt(usex) % 2 == 0) {
                    sex = level_sex_2_name;
                } else {
                    sex = level_sex_1_name;
                }
            }
            return sex;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sex;
    }

    public static int getAgeByIdcardOrBirthday(String idcard,Date birthday){
        int age = getAgeForIdcard(idcard);
        if(age<=0&&birthday!=null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(birthday);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE);
            Calendar cal = Calendar.getInstance();
            age = cal.get(Calendar.YEAR) - year;
            //周岁计算
            if (cal.get(Calendar.MONTH) < (month - 1) || (cal.get(Calendar.MONTH) == (month - 1) && cal.get(Calendar.DATE) < day)) {
                age--;
            }
            return age;
        }
        return age;
    }

    public static String getSexNameForIdcard_new(String idCard){
        String sex = getSexForIdcard_new(idCard);
        if(level_sex_1.equals(sex)){
            return level_sex_1_name;
        }else if(level_sex_2.equals(sex)){
            return level_sex_2_name;
        }else{
            return level_sex_3_name;
        }
    }

    /**
     * 身份证提取出身日期
     *
     * @param card
     * @return
     * @throws Exception
     */
    public static String getBirthdayForIdcardStr(String card)
            throws Exception {
        if (card.length() == 18) {
            String year = card.substring(6).substring(0, 4);// 得到年份
            String yue = card.substring(10).substring(0, 2);// 得到月份
            String ri = card.substring(12).substring(0, 2);// 得到日
            return year + "-" + yue + "-" + ri;
        } else if (card.length() == 15) {
            String uyear = "19" + card.substring(6, 8);// 年份
            String uyue = card.substring(8, 10);// 月份
            String uri = card.substring(10, 12);// 得到日
            return uyear + "-" + uyue + "-" + uri;
        }
        return null;
    }

    /**
     * 身份证提取出身日期
     *
     * @param card
     * @return
     * @throws Exception
     */
    public static String getBirthdayForIdcardStr2(String card)
            throws Exception {
        if (card.length() == 18) {
            String year = card.substring(6).substring(0, 4);// 得到年份
            String yue = card.substring(10).substring(0, 2);// 得到月份
            String ri = card.substring(12).substring(0, 2);// 得到日
            return year  + yue +  ri;
        } else if (card.length() == 15) {
            String uyear = "19" + card.substring(6, 8);// 年份
            String uyue = card.substring(8, 10);// 月份
            String uri = card.substring(10, 12);// 得到日
            return uyear +  uyue  + uri;
        }
        return null;
    }

    /**
     * 身份证提取出身日期
     *
     * @param card
     * @return
     * @throws Exception
     */
    public static String getBirthdayForIdcardStr(String card,Date birthday)
            throws Exception {
        String birth = getBirthdayForIdcardStr(card);
        if(birth == null){
            return DateUtil.dateToStr(birthday,DateUtil.YYYY_MM_DD);
        }
        return birth;
    }


    public static String getIdcardEncode(String idcard) {
        if (idcard != null) {
            if (idcard.length() == 18) {
                return idcard.substring(0, 9) + "*******" + idcard.substring(16, 18);
            } else if (idcard.length() == 15) {
                return idcard.substring(0, 8) + "***" + idcard.substring(11, 15);
            }
        }
        return idcard;
    }

    public static void main(String[] args) {
        String result = "{\"traceid\":\"\",\"flag\":\"1\",\"sign\":\"F7E31BB0BB3114A43C2C869458D86EDC\",\"cause\":\"操作成功\",\"encrypt_data\":{\"date\":\"20221218\",\"yd_jtgj\":\"0\",\"planning_type\":\"\",\"fplist\":[{\"own_pay\":\"0.0\",\"type_name\":\"西药费\",\"uninsurance_pay\":\"0.0\",\"invoice_pay\":\"80.43\",\"special_pay\":\"0.0\",\"type\":\"01\",\"project_pay\":\"80.43\"},{\"own_pay\":\"0.0\",\"type_name\":\"诊察费\",\"uninsurance_pay\":\"0.0\",\"invoice_pay\":\"7.0\",\"special_pay\":\"0.0\",\"type\":\"06\",\"project_pay\":\"7.0\"}],\"jkzh_balance\":\"340.59\",\"planning_date\":\"\",\"treatment_type\":\"01\",\"birth_type\":\"\",\"sex_mc\":\"男\",\"type\":\"11\",\"personal_code\":\"52222419850508381X\",\"psn_no\":\"3500000152222419850508381X01\",\"sbjj_pay\":\"0.0\",\"bjjj_pay\":\"0.0\",\"jtgj_balance\":\"0.00\",\"his_bill_serial\":\"H35020500145202212181109111216\",\"fund_amount\":\"0\",\"insutype\":\"310\",\"tcjj_pay\":\"55.26\",\"pay_time\":\"110952\",\"invoice_xj\":\"\",\"sequence\":\"\",\"account_pay\":\"32.17\",\"total_amount\":\"87.43\",\"name\":\"谭仁祝\",\"bd_jtgj\":\"0\",\"allopatry_type_name\":\"\",\"status\":\"\",\"clr_type\":\"11\",\"insuplc_admdvs\":\"350203\",\"pay_date\":\"20221218\",\"pregnant_days\":\"\",\"account_balance\":\"394.29\",\"treatment_type_name\":\"在职人员\",\"jtgj_pay\":\"0.0\",\"person_cash\":\"0.00\",\"administrative_area\":\"350203\",\"insurance_serial\":\"273486536\",\"pay_standard\":\"0\",\"collector\":\"\",\"bzjj_pay\":\"0.00\",\"certno\":\"52222419850508381X\",\"card_no\":\"\",\"bill_serial\":\"286781547\",\"yljz_pay\":\"0.0\",\"jzfp_pay\":\"0.0\",\"disease_code\":\"\",\"cfdx_money\":\"0\",\"status_name\":\"\",\"invoice_jj\":\"\",\"sex\":\"1\",\"mothed\":\"10\",\"own_jkzh_pay\":\"0\",\"allopatry_type\":\"\",\"own_pay1\":\"32.17\",\"other_pay\":\"0.00\",\"own_pay\":\"0.00\",\"invoice_zh\":\"\",\"fetus_count\":\"\",\"acc_class\":\"01\",\"person_account\":\"32.17\",\"fund_price\":\"0\",\"gwy_pay\":\"0\",\"time\":\"000000\",\"hospitalization_times\":\"0\",\"enterprise_supplement\":\"0.0\",\"region_code\":\"350203\"}}\n" +
                "\n";
        com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(result);

        if (object.getString("flag").equalsIgnoreCase("1")){
            System.out.println("11111");
        }
    }

}
