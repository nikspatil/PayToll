package com.example.paytoll;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class get_toll_details_between_two_stations extends AppCompatActivity {

    Spinner source, destination, mVehicleTypeSpinner;
    String sourceString, destinationString;
    ProgressDialog mProgressDialog;
    Button letsGo;
    private String [] vehicleTypeDropDown = {"Car/Jeep/Van", "LCV", "Bus/Truck", "Upto 3 Axle Vehicle", "4 to 6 Axle Vehicle", "HCM/EME"};
    private String vehicleType;
    private String [] sourceDropDown = {"Thane","Pune", "Mumbai", "Nashik", "Nagpur", "Solapur", "Jalagaon", "Kolhapur","Aurangabad",
                                            "Nanded", "Satara", "Amaravati", "Sangali"};
    private String [] tollplaza = {"Bassi","Thakurtola(End of Durg Bypass)","Bankapur","Hirebagewadi","Durg Bypass (Dhamdanaka)","Hattargi","Kognoli","Tundla","Brahamarakotlu","Cable StayedNainiBridge","Aroli","Dhaneshwar","Fatehpur","Simliya","Omallur","Nathakkarai","Veeracholapuram (West)","Palayam (Dharmapuri)","Rasampalayan","Nemili(Sriperumbudur)","Joya","Khalghat -MP/Maharashtra Border(Sendhwa, Jamli)","Boothakudi","Chittampatti","Banglore -Neelamangla","Chalageri","Choundha","Brijghat","Usaka ( Chamari)","Indore Dewas  (Indore Bypass)","Dasna","Siwaya","Daffi","Mohania","Sasaram","Anantram","Tendua (Gorakhpur Bypass)","Barajore (Bara)","Bharudi","Pithadiya","Choryasi","Anewadi","Mettupatti","Doddakarenahalli (Neelmangala )","Thirumandurai","Paduna","Patas","Thandikhui","Daroda","Samakhiyali","Kelapur","Nandgaon Peth","Mundiyar","Raksha","Ramnagar","Athur (Tindivanam)","Vanagaram (Chennai Bypass)","Mandawnagar","Surapattu (Chennai Bypass II)","Paranur (Chengalpet)","Nallur","Allonia","Eliyarpathi","Pudurpandiyapuram","Guna Bypass","Malthone","Badarpur","Titarpani","Gurau (Semra Atikabad)","Jajau (OldBaretha)","Vaiguntham","Makhel","Vijaymangalmam","Bhiladi","Kumbalam","Varahi","Velanchettiyur","Krishnagiri","Vikravandi(Villupuram)","Pallikonda","Morattadi","Vaniyambadi","Nanguneri","Kodai Road","Ludhawai","Etturvattam","Kappalur","Salaipudhur","Paliyekkara","Valavanthankottai","Karbylu (Bellur Cross)","Pudukottai(Vagaikulam)","Samayapuram","Sengurichi","Korai","Khalghat  (Indore - Khalghat)","Parsoni Khem","Kishangarh(Badgaon)","Maithi","Tatiyawas","Chiddan","Dappar","Gogunda (Jaswantgarh)","Kurali (Behrampur)","Jojro Ka Khera","Kedi","KhandiObari","Narayanpura","Malera","Ghoti","Vaghasia","LakhanpurRajbagh","Dhumiyani","Chandwad","Khedshivapur","Baswant(Pimplegaon)","Sendurwafa","Shirpur","Gondhkhairi Kondhali","Chandimandir","Dighal","Dhilwan","Gangaycha Jatt","Nijjarpura","Panipat Elevated","Gharaunda(Karnal)","Sambhu(Ghaggar)","Ladowal","Karanja (Ghadge)","Srinagar","Mahuvan","Lalanagar","Salemgarh","Muziana Hetim","Chaukadi","Katoghan","Gurapalli","Srirampur","Gudipada (Old Gangapada)","Sergarh","Panikholi","Manguli","Krishnavaram","Rolmamda","Manoharabad","Gamjal","Pippalwada","Pundag","Sikandra","Rajadhok","Amoli","Thikariya(Jaip","Barkheda (Chandlai)","Sonwa (Sonva)","Lambiya Kalan","Hoskote","Bagepalli","Kirasave","Shanthigrama","Bann","Harsa Mansar","Arjunali","Mansar (Kamptee - Kanhan Bypass)/ Tekadi","Borkhedi (Nagpur ByPass)","Laling (Dhule)","Vanana","Ait","Vighakhet","Khemana","Laxmipuram","Raje(Old Naraur)","Chilakapalem","Indalwai","Sardewadi /Indapur","Itunja (Barabhari)","Ahmadpur","Ronahi","Nawabganj","Shahjahanpur","Manoharpur","Daulatpura","Raikal","Shakhapur","Gudur (Hyd-Yadgiri)","Pullur","Vempadu","Panthangi","Paschim Madati","Rampura","Rasoiya Dhamna","Ghanghri","Keesara","Kaza","Budhanam","Marur","Debra","Sonapetya","Bellupada","Madapam","Aganampudi","Balgudar","Saukala","Ponnambalapatti","Main Toll(Panchvati colony) (Sheela Nagar)","Kasaba (Bijapur)","Venkatachalam (Old Nellore)","Undavariya/ Sirohi","Indore Dewas (Indore side)","Nathavalasa/Vizianagaram","Pottipadu","Kalaparru","Sullurpet","Sunambatti (Musunuru)","Bolapalli","Tanguturu","Kasepalli","Tasawade","Kini","Amakathadu","Bhalgam (Belgaum)","Laxmannath","Varwade","Gwalior Bypass (Mehra)","Surajbari","Sawaleshwar","Chitora","Bakoli","Guilalu","Karjeevanhalli","Navayuga Devanahalli / Sadahalli / Pujanahalli","Kaythsandra/Tumkur/Chokkanahalli",
            "Kherki Daula","Palsit","Badauri","Nivedita Setu","Dankuni","Jaladhulagori(Dhulagarh)","Surjapur","Beliyad","Hariabara","Barsoni","Kharik","Maranga","Boriach","Bhagwada","Charoti","Khaniwade","Dahar","Maklu","Chennasamudram","Songir","IGI Airport","Shahapur","Vanagari","BharthanKarjan","Deingpasoh","Thirupparaithurai","Manavasi","Lechchumanapatti","Vantada","Kathpur","Lambalakudi","AhmedabadVadod","Attibele (BETL)", "Neelamangala  - Tumkur /Kulumepalya","Elevated Section/ Electronic City","Nagarhalla","Gabbur (MoRTH)","Narendra (MoRTH)","Pamban Bridge(MoRTH)","KaliaBhomoraBridge (MoRTH)","Gangadhar Bridge (MoRTH)","Baggad (MoRTH)","Bagaliya (MoRTH)","Bagodara (MoRTH)","Kharpada (MoRTH)","Jia Bridge(MoRTH)","Savitri (MoRTH)","Nashirabad (MoRTH)","Fekri (MoRTH)","Kharegaon (MoRTH)","Moshi (MoRTH)","Parvati Bridge (MoRTH)","Chandloi/ Rajgurunagar(MoRTH)","Ashoka DSC Katni bypass (MoRTH)","Kawdipath (MoRTH)","Kasurdi (MoRTH)","Koshta (MoRTH)","Kardha (MoRTH)","Tiwasa (MoRTH)","Nardana(MoRTH)","Baretha(MoRTH)","Wadakbal (MoRTH)","Barahi (MoRTH)","Bamanbore (MoRTH)","Patancheru (Prograssive Construction Toll)(MoRTH)","RawasonBridge (MoRTH)","Nandghat Bridge (MoRTH)","Kharun (MoRTH)","Kosa Nala (MoRTH)","Kundannur (MoRTH)","Akkulam (MoRTH)","Varapuzha Bridge(MoRTH)","Bhatwada (Godhra)","Chollang","Chillakallu","Korlaphadu","Hitnal","Madrak","Baros","Khairabad(Karondi)","Pithai","Vavadikhurd","Mulbagal","Kishorpur(Chandermore)","Gopgrapara","Niyamatpur Ekrotiya","Thriyakhetal","Guabari","Fulara","Chikhalikala","Jungawani","Jaitpur","DakhinaSekhpur","Kelwad","Chullimada Hamlet (Pampampallam)","Aliyapur","Khanna","Baragarh (Barhaguda)","Ponnarimangalam","Ladpalwan","Waryam Nangal","Deederganj","Akhepura","Milanpur","Khambara","Patanswangi","Gegal","Pipalaz","Gadoi","Dari","Raha(Saragaon)","Daboka","Nazira Khat","Mandal (Vyara)","Bhatia","Luharali","Somna","Lakholi","Sundar Nagar","Raipur","Indranagar","Birami","Uthamam","Gaddurur","WEPL Mathani","Runni","S V Puram","Pattaraiperumbudur (temporary TP)","Paat Sahanipur (Pipli)","Methoon","Kaniyur","Mokha","Eethakota","Unguturu","Chagalmarri","Palempalli","Pahammawlein","Rohad","Kishorpura","Mandawada (Gomati)","Negadiya","Surathkal","Bhiknoor","Kunwarpur","Nuruddinpur","Chuipali","Dhank","Vasad","Radhvanaj (Kheda)","Madina Korsan","Ramayana","Para","SaidpurPatedha","Lilamba","Aaini","Gulalpurva","Sonvay (Indore - Khalghat)","Chalakwadi","Hivargaon Pavsa","Nimbaniya Ki Dhani(Bayatu)","Gundmi","Hazamady","Talapady","Kadthal","Konetipuram","Chinthapally","Lakshmipur","Chakseherdi (Gajol) Bagsarai","Mahanth Maniyari","Govindpur","Konder","Chila Chond","Raibha","Mada","Nashri","Banthadi","Tamdoli","Rupa Kheda","Mujras","SixML","Lasedi","Dhadhar","Shobhasar","Rapur","Chintalapalem","Badava","Shenbagampettai","Jasnathnagar","Motisar (Khanori)","Morani (Pokaran)","Mekalavaripalli","Raviguntapalli","Brahmanapalli","Hasanpur (Sainkul)","Khantaghar (Dhenkikote)","Banajodi (Padmapur)","Khadda","Sonvarsaa","Landhari","Narwana","Badopatti","Chaudhriwas","Kalajhar","Badbar","Bhagan","Sosokhurd","Tand Balidih","Doli","PalayaGandharvakottai","Mangalgi","Kamkole","Yedeshi","Tamalwadi","Deoria","Jogipura","Pagara","Sakatpura(LHS)(Staggered)","Nayagaon(RHS)(Stagg)","Mandava","Basapuram","Nannur","Purankhedi","Banskopa","Semri","Banushi","Hathitala","Bor Charnan","Harimma","Nimbi Jodha","Jindpur","Aihar","Limdi","MilkMajra","Durgamvaripalli","Buchireddypalem","DC PALLI","Zidda","Leherabega","Paind","Dhareri jattan","Azizpur","Bhavdeen","Halaharvi","Ramdevra","LATHI","Mudkhera","Panihaar","Salasar","Nokhra","Kheerwa","Khuian Malkan","Pasyih","Lomshinong","Davuluru","Pandutala","Thiruppachethi","Mohammad Ibrahimpur","Dumberwadi","Sonho","Pokhraira","Belon","Hadawa","Chhajarsi","Bara","Asroga","Kherwasani","Bogalur","Dhoki","Majhgawan","Pathoroundi","Shahdol","Rojwas","Dattigaon","Methwada","Muthojipet","Amdi","Pargaon","Padalshingi","Bhokarwadi Maliwadi","Jagtara","Mashora","Badayiguda","Saliwada","Aksada","Chhapwa","Nainsar","Mohtara","Odhaki Paipkhar","Gollaprolu","Mathur","Dilawarpur","Mandana","Kair Fakir ki Dhani","Pidhi","Aindhi","Sabli","Patgaon","Kannolli","Harval","Talmod","Phulwadi","Boharipar (Khursi)","Sehatganj","Husnapur","Belekeri","Holegadde","Shirur","Sali Bamandanga","Nawabganj (Adampur)Allahabad Bypass","Ashiv"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_toll_details_between_two_stations);
        source = (Spinner)findViewById(R.id.sourcespinner);
        destination = (Spinner)findViewById(R.id.destinationspinner);
        mVehicleTypeSpinner = (Spinner) findViewById(R.id.main_vehicle_type_spinner);
        letsGo = (Button) findViewById(R.id.letsgo_btn);
        source.setAdapter(new ArrayAdapter<>(get_toll_details_between_two_stations.this,
                android.R.layout.simple_spinner_dropdown_item, sourceDropDown));

        mProgressDialog = new ProgressDialog(this);
        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please select source location", Toast.LENGTH_LONG).show();

                }else{
                    sourceString = adapterView.getItemAtPosition(i).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        destination.setAdapter(new ArrayAdapter<>(get_toll_details_between_two_stations.this,
                android.R.layout.simple_spinner_dropdown_item, sourceDropDown));

        destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please select destination location", Toast.LENGTH_LONG).show();

                }else{
                    destinationString = adapterView.getItemAtPosition(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter_role = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vehicleTypeDropDown);
        adapter_role.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mVehicleTypeSpinner.setAdapter(adapter_role);

        mVehicleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                vehicleType = (String) mVehicleTypeSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(get_toll_details_between_two_stations.this, "Please Select Vehicle Type", Toast.LENGTH_SHORT).show();

            }
        });
        letsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressDialog.setTitle("Please wait...");
                mProgressDialog.setMessage("It will take time according to your internet connection");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
               
                String from = sourceString;
                String to = destinationString;
                if(from == "" && to == "")
                {
                    Toast.makeText(get_toll_details_between_two_stations.this,"Please select source & destination", Toast.LENGTH_LONG).show();
                }
                else if(vehicleType == ""){
                    Toast.makeText(get_toll_details_between_two_stations.this, "Please select vehicle type", Toast.LENGTH_LONG).show();
                }
                if(from != "" && to != "" && vehicleType != "") {
                    startActivity(new Intent(get_toll_details_between_two_stations.this, scanqrCode.class));
                    finish();
                }

            }
        });
    }
}