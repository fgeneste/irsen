// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
//
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.

package irsen.test_delete_0_1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import routines.DataOperation;
import routines.Mathematical;
import routines.Numeric;
import routines.Relational;
import routines.StringHandling;
import routines.TalendDataGenerator;
import routines.TalendDate;
import routines.TalendString;
import routines.TalendStringUtil;
import routines.system.*;
import routines.system.api.*;

@SuppressWarnings("unused")
/**
 * Job: Test_delete Purpose: <br>
 * Description: <br>
 *
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class Test_delete implements TalendJob {

    protected static void logIgnoredError(String message, Throwable cause) {
        System.err.println(message);
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    public final Object obj = new Object();

    // for transmiting parameters purpose
    private Object valueObject = null;

    public Object getValueObject() {
        return this.valueObject;
    }

    public void setValueObject(Object valueObject) {
        this.valueObject = valueObject;
    }

    private static final String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

    private static final String utf8Charset = "UTF-8";

    // contains type for every context property
    public class PropertiesWithType extends java.util.Properties {

        private static final long serialVersionUID = 1L;
        private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

        public PropertiesWithType(java.util.Properties properties) {
            super(properties);
        }

        public PropertiesWithType() {
            super();
        }

        public void setContextType(String key, String type) {
            propertyTypes.put(key, type);
        }

        public String getContextType(String key) {
            return propertyTypes.get(key);
        }
    }

    // create and load default properties
    private java.util.Properties defaultProps = new java.util.Properties();

    // create application properties with default
    public class ContextProperties extends PropertiesWithType {

        private static final long serialVersionUID = 1L;

        public ContextProperties(java.util.Properties properties) {
            super(properties);
        }

        public ContextProperties() {
            super();
        }

        public void synchronizeContext() {}

        // if the stored or passed value is "<TALEND_NULL>" string, it mean null
        public String getStringValue(String key) {
            String origin_value = this.getProperty(key);
            if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
                return null;
            }
            return origin_value;
        }
    }

    protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

    public ContextProperties getContext() {
        return this.context;
    }

    private final String jobVersion = "0.1";
    private final String jobName = "Test_delete";
    private final String projectName = "IRSEN";
    public Integer errorCode = null;
    private String currentComponent = "";

    private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
    private static final java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

    private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
    private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
    private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
    public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

    private RunStat runStat = new RunStat();

    // OSGi DataSource
    private static final String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

    private static final String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

    public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
        java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
        for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
            talendDataSources.put(dataSourceEntry.getKey(), new routines.system.TalendDataSource(dataSourceEntry.getValue()));
        }
        globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
        globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
    }

    public void setDataSourceReferences(List serviceReferences) throws Exception {
        java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
        java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

        for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
            .getServices(serviceReferences, javax.sql.DataSource.class)
            .entrySet()) {
            dataSources.put(entry.getKey(), entry.getValue());
            talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
        }

        globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
        globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
    }

    private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
    private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

    public String getExceptionStackTrace() {
        if ("failure".equals(this.getStatus())) {
            errorMessagePS.flush();
            return baos.toString();
        }
        return null;
    }

    private Exception exception;

    public Exception getException() {
        if ("failure".equals(this.getStatus())) {
            return this.exception;
        }
        return null;
    }

    private class TalendException extends Exception {

        private static final long serialVersionUID = 1L;

        private java.util.Map<String, Object> globalMap = null;
        private Exception e = null;
        private String currentComponent = null;
        private String virtualComponentName = null;

        public void setVirtualComponentName(String virtualComponentName) {
            this.virtualComponentName = virtualComponentName;
        }

        private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
            this.currentComponent = errorComponent;
            this.globalMap = globalMap;
            this.e = e;
        }

        public Exception getException() {
            return this.e;
        }

        public String getCurrentComponent() {
            return this.currentComponent;
        }

        public String getExceptionCauseMessage(Exception e) {
            Throwable cause = e;
            String message = null;
            int i = 10;
            while (null != cause && 0 < i--) {
                message = cause.getMessage();
                if (null == message) {
                    cause = cause.getCause();
                } else {
                    break;
                }
            }
            if (null == message) {
                message = e.getClass().getName();
            }
            return message;
        }

        @Override
        public void printStackTrace() {
            if (!(e instanceof TalendException || e instanceof TDieException)) {
                if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
                    globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
                }
                globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
                System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
            }
            if (!(e instanceof TDieException)) {
                if (e instanceof TalendException) {
                    e.printStackTrace();
                } else {
                    e.printStackTrace();
                    e.printStackTrace(errorMessagePS);
                    Test_delete.this.exception = e;
                }
            }
            if (!(e instanceof TalendException)) {
                try {
                    for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
                        if (m.getName().compareTo(currentComponent + "_error") == 0) {
                            m.invoke(Test_delete.this, new Object[] { e, currentComponent, globalMap });
                            break;
                        }
                    }

                    if (!(e instanceof TDieException)) {}
                } catch (Exception e) {
                    this.e.printStackTrace();
                }
            }
        }
    }

    public void tDBRow_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBRow_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tFlowToIterate_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBRow_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBRow_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBRow_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tLogRow_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBRow_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBRow_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        resumeUtil.addLog(
            "SYSTEM_LOG",
            "NODE:" + errorComponent,
            "",
            Thread.currentThread().getId() + "",
            "FATAL",
            "",
            exception.getMessage(),
            ResumeUtil.getExceptionStackTrace(exception),
            ""
        );
    }

    public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {

        static final byte[] commonByteArrayLock_IRSEN_Test_delete = new byte[0];
        static byte[] commonByteArray_IRSEN_Test_delete = new byte[0];

        public String senmat;

        public String getSenmat() {
            return this.senmat;
        }

        public String quacod;

        public String getQuacod() {
            return this.quacod;
        }

        public String sennomuse;

        public String getSennomuse() {
            return this.sennomuse;
        }

        public String sennompat;

        public String getSennompat() {
            return this.sennompat;
        }

        public String sennommar;

        public String getSennommar() {
            return this.sennommar;
        }

        public String sennomtec;

        public String getSennomtec() {
            return this.sennomtec;
        }

        public String senprenomuse;

        public String getSenprenomuse() {
            return this.senprenomuse;
        }

        public String senprenomciv;

        public String getSenprenomciv() {
            return this.senprenomciv;
        }

        public java.util.Date sendatnai;

        public java.util.Date getSendatnai() {
            return this.sendatnai;
        }

        public String senlienai;

        public String getSenlienai() {
            return this.senlienai;
        }

        public java.util.Date sendatdec;

        public java.util.Date getSendatdec() {
            return this.sendatdec;
        }

        public String etasencod;

        public String getEtasencod() {
            return this.etasencod;
        }

        public String sendespro;

        public String getSendespro() {
            return this.sendespro;
        }

        public String pcscod;

        public String getPcscod() {
            return this.pcscod;
        }

        public String catprocod;

        public String getCatprocod() {
            return this.catprocod;
        }

        public Long senrngprt;

        public Long getSenrngprt() {
            return this.senrngprt;
        }

        public String sengrppolcodcou;

        public String getSengrppolcodcou() {
            return this.sengrppolcodcou;
        }

        public String sengrppolliccou;

        public String getSengrppolliccou() {
            return this.sengrppolliccou;
        }

        public String sentypappcou;

        public String getSentypappcou() {
            return this.sentypappcou;
        }

        public String sencomcodcou;

        public String getSencomcodcou() {
            return this.sencomcodcou;
        }

        public String sencomliccou;

        public String getSencomliccou() {
            return this.sencomliccou;
        }

        public Long sencirnumcou;

        public Long getSencirnumcou() {
            return this.sencirnumcou;
        }

        public String sencircou;

        public String getSencircou() {
            return this.sencircou;
        }

        public String senburliccou;

        public String getSenburliccou() {
            return this.senburliccou;
        }

        public String senema;

        public String getSenema() {
            return this.senema;
        }

        public String sennumtelsen;

        public String getSennumtelsen() {
            return this.sennumtelsen;
        }

        public String sendiplome;

        public String getSendiplome() {
            return this.sendiplome;
        }

        public String sendecora;

        public String getSendecora() {
            return this.sendecora;
        }

        public java.util.Date sendatpreele;

        public java.util.Date getSendatpreele() {
            return this.sendatpreele;
        }

        public Long indid;

        public Long getIndid() {
            return this.indid;
        }

        public String sennomusecap;

        public String getSennomusecap() {
            return this.sennomusecap;
        }

        public String senpagper;

        public String getSenpagper() {
            return this.senpagper;
        }

        public String senrngprtcod;

        public String getSenrngprtcod() {
            return this.senrngprtcod;
        }

        public String parpolorgcod;

        public String getParpolorgcod() {
            return this.parpolorgcod;
        }

        public String senliedec;

        public String getSenliedec() {
            return this.senliedec;
        }

        public Long sendptnumnai;

        public Long getSendptnumnai() {
            return this.sendptnumnai;
        }

        public Long sendptnumdec;

        public Long getSendptnumdec() {
            return this.sendptnumdec;
        }

        public String senauttra;

        public String getSenauttra() {
            return this.senauttra;
        }

        public String senlog;

        public String getSenlog() {
            return this.senlog;
        }

        public String sencrinom;

        public String getSencrinom() {
            return this.sencrinom;
        }

        public String senfem;

        public String getSenfem() {
            return this.senfem;
        }

        public String senautema;

        public String getSenautema() {
            return this.senautema;
        }

        public String senautgrpsen;

        public String getSenautgrpsen() {
            return this.senautgrpsen;
        }

        public String senautpagper;

        public String getSenautpagper() {
            return this.senautpagper;
        }

        public Long sennbrque;

        public Long getSennbrque() {
            return this.sennbrque;
        }

        public String sennomdis;

        public String getSennomdis() {
            return this.sennomdis;
        }

        public String numsen;

        public String getNumsen() {
            return this.numsen;
        }

        public Long sennumsie;

        public Long getSennumsie() {
            return this.sennumsie;
        }

        public Long sennbrvid;

        public Long getSennbrvid() {
            return this.sennbrvid;
        }

        public String sennomdit;

        public String getSennomdit() {
            return this.sennomdit;
        }

        public String titnobcod;

        public String getTitnobcod() {
            return this.titnobcod;
        }

        public Long senannnai;

        public Long getSenannnai() {
            return this.senannnai;
        }

        public Long senmoinai;

        public Long getSenmoinai() {
            return this.senmoinai;
        }

        public Long senanndec;

        public Long getSenanndec() {
            return this.senanndec;
        }

        public Long senmoidec;

        public Long getSenmoidec() {
            return this.senmoidec;
        }

        public String senobs4r1;

        public String getSenobs4r1() {
            return this.senobs4r1;
        }

        public String senobs4r2;

        public String getSenobs4r2() {
            return this.senobs4r2;
        }

        public String senobs3r1;

        public String getSenobs3r1() {
            return this.senobs3r1;
        }

        public String senobs3r2;

        public String getSenobs3r2() {
            return this.senobs3r2;
        }

        public java.util.Date sendatderele;

        public java.util.Date getSendatderele() {
            return this.sendatderele;
        }

        public String catprocod4r;

        public String getCatprocod4r() {
            return this.catprocod4r;
        }

        public String catprocod3r;

        public String getCatprocod3r() {
            return this.catprocod3r;
        }

        public String sendespro4r;

        public String getSendespro4r() {
            return this.sendespro4r;
        }

        public String sendespro3r;

        public String getSendespro3r() {
            return this.sendespro3r;
        }

        public Long sencirnumcou4r;

        public Long getSencirnumcou4r() {
            return this.sencirnumcou4r;
        }

        public String sencircou4r;

        public String getSencircou4r() {
            return this.sencircou4r;
        }

        public Long sencirnumcou3r;

        public Long getSencirnumcou3r() {
            return this.sencirnumcou3r;
        }

        public String sencircou3r;

        public String getSencircou3r() {
            return this.sencircou3r;
        }

        public String sengrppolcodcou4r;

        public String getSengrppolcodcou4r() {
            return this.sengrppolcodcou4r;
        }

        public String sentypappcou4r;

        public String getSentypappcou4r() {
            return this.sentypappcou4r;
        }

        public java.util.Date syscredat;

        public java.util.Date getSyscredat() {
            return this.syscredat;
        }

        public String syscrelog;

        public String getSyscrelog() {
            return this.syscrelog;
        }

        public java.util.Date sysmajdat;

        public java.util.Date getSysmajdat() {
            return this.sysmajdat;
        }

        public String sysmajlog;

        public String getSysmajlog() {
            return this.sysmajlog;
        }

        public String autgrpsencod;

        public String getAutgrpsencod() {
            return this.autgrpsencod;
        }

        public String catprocod2e;

        public String getCatprocod2e() {
            return this.catprocod2e;
        }

        public String sendespro2e;

        public String getSendespro2e() {
            return this.sendespro2e;
        }

        public String sendaiurl;

        public String getSendaiurl() {
            return this.sendaiurl;
        }

        public String sengrppolcommu;

        public String getSengrppolcommu() {
            return this.sengrppolcommu;
        }

        public String senobscommu;

        public String getSenobscommu() {
            return this.senobscommu;
        }

        public String senburcommu;

        public String getSenburcommu() {
            return this.senburcommu;
        }

        public String identifiant_rne;

        public String getIdentifiant_rne() {
            return this.identifiant_rne;
        }

        public String identifiant_assnat;

        public String getIdentifiant_assnat() {
            return this.identifiant_assnat;
        }

        public String titre_pair_france;

        public String getTitre_pair_france() {
            return this.titre_pair_france;
        }

        public String lister_titre_pair_france;

        public String getLister_titre_pair_france() {
            return this.lister_titre_pair_france;
        }

        public String fonction_pair;

        public String getFonction_pair() {
            return this.fonction_pair;
        }

        public String credit_portrait;

        public String getCredit_portrait() {
            return this.credit_portrait;
        }

        public String lister_autre_titre_pair_france;

        public String getLister_autre_titre_pair_france() {
            return this.lister_autre_titre_pair_france;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Test_delete.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Test_delete.length == 0) {
                        commonByteArray_IRSEN_Test_delete = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Test_delete = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Test_delete, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Test_delete, 0, length, utf8Charset);
            }
            return strReturn;
        }

        private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            String strReturn = null;
            int length = 0;
            length = unmarshaller.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Test_delete.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Test_delete.length == 0) {
                        commonByteArray_IRSEN_Test_delete = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Test_delete = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Test_delete, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Test_delete, 0, length, utf8Charset);
            }
            return strReturn;
        }

        private void writeString(String str, ObjectOutputStream dos) throws IOException {
            if (str == null) {
                dos.writeInt(-1);
            } else {
                byte[] byteArray = str.getBytes(utf8Charset);
                dos.writeInt(byteArray.length);
                dos.write(byteArray);
            }
        }

        private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (str == null) {
                marshaller.writeInt(-1);
            } else {
                byte[] byteArray = str.getBytes(utf8Charset);
                marshaller.writeInt(byteArray.length);
                marshaller.write(byteArray);
            }
        }

        private java.util.Date readDate(ObjectInputStream dis) throws IOException {
            java.util.Date dateReturn = null;
            int length = 0;
            length = dis.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(dis.readLong());
            }
            return dateReturn;
        }

        private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            java.util.Date dateReturn = null;
            int length = 0;
            length = unmarshaller.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(unmarshaller.readLong());
            }
            return dateReturn;
        }

        private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
            if (date1 == null) {
                dos.writeByte(-1);
            } else {
                dos.writeByte(0);
                dos.writeLong(date1.getTime());
            }
        }

        private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (date1 == null) {
                marshaller.writeByte(-1);
            } else {
                marshaller.writeByte(0);
                marshaller.writeLong(date1.getTime());
            }
        }

        public void readData(ObjectInputStream dis) {
            synchronized (commonByteArrayLock_IRSEN_Test_delete) {
                try {
                    int length = 0;

                    this.senmat = readString(dis);

                    this.quacod = readString(dis);

                    this.sennomuse = readString(dis);

                    this.sennompat = readString(dis);

                    this.sennommar = readString(dis);

                    this.sennomtec = readString(dis);

                    this.senprenomuse = readString(dis);

                    this.senprenomciv = readString(dis);

                    this.sendatnai = readDate(dis);

                    this.senlienai = readString(dis);

                    this.sendatdec = readDate(dis);

                    this.etasencod = readString(dis);

                    this.sendespro = readString(dis);

                    this.pcscod = readString(dis);

                    this.catprocod = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.senrngprt = null;
                    } else {
                        this.senrngprt = dis.readLong();
                    }

                    this.sengrppolcodcou = readString(dis);

                    this.sengrppolliccou = readString(dis);

                    this.sentypappcou = readString(dis);

                    this.sencomcodcou = readString(dis);

                    this.sencomliccou = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sencirnumcou = null;
                    } else {
                        this.sencirnumcou = dis.readLong();
                    }

                    this.sencircou = readString(dis);

                    this.senburliccou = readString(dis);

                    this.senema = readString(dis);

                    this.sennumtelsen = readString(dis);

                    this.sendiplome = readString(dis);

                    this.sendecora = readString(dis);

                    this.sendatpreele = readDate(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.indid = null;
                    } else {
                        this.indid = dis.readLong();
                    }

                    this.sennomusecap = readString(dis);

                    this.senpagper = readString(dis);

                    this.senrngprtcod = readString(dis);

                    this.parpolorgcod = readString(dis);

                    this.senliedec = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sendptnumnai = null;
                    } else {
                        this.sendptnumnai = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.sendptnumdec = null;
                    } else {
                        this.sendptnumdec = dis.readLong();
                    }

                    this.senauttra = readString(dis);

                    this.senlog = readString(dis);

                    this.sencrinom = readString(dis);

                    this.senfem = readString(dis);

                    this.senautema = readString(dis);

                    this.senautgrpsen = readString(dis);

                    this.senautpagper = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sennbrque = null;
                    } else {
                        this.sennbrque = dis.readLong();
                    }

                    this.sennomdis = readString(dis);

                    this.numsen = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sennumsie = null;
                    } else {
                        this.sennumsie = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.sennbrvid = null;
                    } else {
                        this.sennbrvid = dis.readLong();
                    }

                    this.sennomdit = readString(dis);

                    this.titnobcod = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.senannnai = null;
                    } else {
                        this.senannnai = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.senmoinai = null;
                    } else {
                        this.senmoinai = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.senanndec = null;
                    } else {
                        this.senanndec = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.senmoidec = null;
                    } else {
                        this.senmoidec = dis.readLong();
                    }

                    this.senobs4r1 = readString(dis);

                    this.senobs4r2 = readString(dis);

                    this.senobs3r1 = readString(dis);

                    this.senobs3r2 = readString(dis);

                    this.sendatderele = readDate(dis);

                    this.catprocod4r = readString(dis);

                    this.catprocod3r = readString(dis);

                    this.sendespro4r = readString(dis);

                    this.sendespro3r = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sencirnumcou4r = null;
                    } else {
                        this.sencirnumcou4r = dis.readLong();
                    }

                    this.sencircou4r = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sencirnumcou3r = null;
                    } else {
                        this.sencirnumcou3r = dis.readLong();
                    }

                    this.sencircou3r = readString(dis);

                    this.sengrppolcodcou4r = readString(dis);

                    this.sentypappcou4r = readString(dis);

                    this.syscredat = readDate(dis);

                    this.syscrelog = readString(dis);

                    this.sysmajdat = readDate(dis);

                    this.sysmajlog = readString(dis);

                    this.autgrpsencod = readString(dis);

                    this.catprocod2e = readString(dis);

                    this.sendespro2e = readString(dis);

                    this.sendaiurl = readString(dis);

                    this.sengrppolcommu = readString(dis);

                    this.senobscommu = readString(dis);

                    this.senburcommu = readString(dis);

                    this.identifiant_rne = readString(dis);

                    this.identifiant_assnat = readString(dis);

                    this.titre_pair_france = readString(dis);

                    this.lister_titre_pair_france = readString(dis);

                    this.fonction_pair = readString(dis);

                    this.credit_portrait = readString(dis);

                    this.lister_autre_titre_pair_france = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Test_delete) {
                try {
                    int length = 0;

                    this.senmat = readString(dis);

                    this.quacod = readString(dis);

                    this.sennomuse = readString(dis);

                    this.sennompat = readString(dis);

                    this.sennommar = readString(dis);

                    this.sennomtec = readString(dis);

                    this.senprenomuse = readString(dis);

                    this.senprenomciv = readString(dis);

                    this.sendatnai = readDate(dis);

                    this.senlienai = readString(dis);

                    this.sendatdec = readDate(dis);

                    this.etasencod = readString(dis);

                    this.sendespro = readString(dis);

                    this.pcscod = readString(dis);

                    this.catprocod = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.senrngprt = null;
                    } else {
                        this.senrngprt = dis.readLong();
                    }

                    this.sengrppolcodcou = readString(dis);

                    this.sengrppolliccou = readString(dis);

                    this.sentypappcou = readString(dis);

                    this.sencomcodcou = readString(dis);

                    this.sencomliccou = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sencirnumcou = null;
                    } else {
                        this.sencirnumcou = dis.readLong();
                    }

                    this.sencircou = readString(dis);

                    this.senburliccou = readString(dis);

                    this.senema = readString(dis);

                    this.sennumtelsen = readString(dis);

                    this.sendiplome = readString(dis);

                    this.sendecora = readString(dis);

                    this.sendatpreele = readDate(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.indid = null;
                    } else {
                        this.indid = dis.readLong();
                    }

                    this.sennomusecap = readString(dis);

                    this.senpagper = readString(dis);

                    this.senrngprtcod = readString(dis);

                    this.parpolorgcod = readString(dis);

                    this.senliedec = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sendptnumnai = null;
                    } else {
                        this.sendptnumnai = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.sendptnumdec = null;
                    } else {
                        this.sendptnumdec = dis.readLong();
                    }

                    this.senauttra = readString(dis);

                    this.senlog = readString(dis);

                    this.sencrinom = readString(dis);

                    this.senfem = readString(dis);

                    this.senautema = readString(dis);

                    this.senautgrpsen = readString(dis);

                    this.senautpagper = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sennbrque = null;
                    } else {
                        this.sennbrque = dis.readLong();
                    }

                    this.sennomdis = readString(dis);

                    this.numsen = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sennumsie = null;
                    } else {
                        this.sennumsie = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.sennbrvid = null;
                    } else {
                        this.sennbrvid = dis.readLong();
                    }

                    this.sennomdit = readString(dis);

                    this.titnobcod = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.senannnai = null;
                    } else {
                        this.senannnai = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.senmoinai = null;
                    } else {
                        this.senmoinai = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.senanndec = null;
                    } else {
                        this.senanndec = dis.readLong();
                    }

                    length = dis.readByte();
                    if (length == -1) {
                        this.senmoidec = null;
                    } else {
                        this.senmoidec = dis.readLong();
                    }

                    this.senobs4r1 = readString(dis);

                    this.senobs4r2 = readString(dis);

                    this.senobs3r1 = readString(dis);

                    this.senobs3r2 = readString(dis);

                    this.sendatderele = readDate(dis);

                    this.catprocod4r = readString(dis);

                    this.catprocod3r = readString(dis);

                    this.sendespro4r = readString(dis);

                    this.sendespro3r = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sencirnumcou4r = null;
                    } else {
                        this.sencirnumcou4r = dis.readLong();
                    }

                    this.sencircou4r = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sencirnumcou3r = null;
                    } else {
                        this.sencirnumcou3r = dis.readLong();
                    }

                    this.sencircou3r = readString(dis);

                    this.sengrppolcodcou4r = readString(dis);

                    this.sentypappcou4r = readString(dis);

                    this.syscredat = readDate(dis);

                    this.syscrelog = readString(dis);

                    this.sysmajdat = readDate(dis);

                    this.sysmajlog = readString(dis);

                    this.autgrpsencod = readString(dis);

                    this.catprocod2e = readString(dis);

                    this.sendespro2e = readString(dis);

                    this.sendaiurl = readString(dis);

                    this.sengrppolcommu = readString(dis);

                    this.senobscommu = readString(dis);

                    this.senburcommu = readString(dis);

                    this.identifiant_rne = readString(dis);

                    this.identifiant_assnat = readString(dis);

                    this.titre_pair_france = readString(dis);

                    this.lister_titre_pair_france = readString(dis);

                    this.fonction_pair = readString(dis);

                    this.credit_portrait = readString(dis);

                    this.lister_autre_titre_pair_france = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void writeData(ObjectOutputStream dos) {
            try {
                // String

                writeString(this.senmat, dos);

                // String

                writeString(this.quacod, dos);

                // String

                writeString(this.sennomuse, dos);

                // String

                writeString(this.sennompat, dos);

                // String

                writeString(this.sennommar, dos);

                // String

                writeString(this.sennomtec, dos);

                // String

                writeString(this.senprenomuse, dos);

                // String

                writeString(this.senprenomciv, dos);

                // java.util.Date

                writeDate(this.sendatnai, dos);

                // String

                writeString(this.senlienai, dos);

                // java.util.Date

                writeDate(this.sendatdec, dos);

                // String

                writeString(this.etasencod, dos);

                // String

                writeString(this.sendespro, dos);

                // String

                writeString(this.pcscod, dos);

                // String

                writeString(this.catprocod, dos);

                // Long

                if (this.senrngprt == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senrngprt);
                }

                // String

                writeString(this.sengrppolcodcou, dos);

                // String

                writeString(this.sengrppolliccou, dos);

                // String

                writeString(this.sentypappcou, dos);

                // String

                writeString(this.sencomcodcou, dos);

                // String

                writeString(this.sencomliccou, dos);

                // Long

                if (this.sencirnumcou == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sencirnumcou);
                }

                // String

                writeString(this.sencircou, dos);

                // String

                writeString(this.senburliccou, dos);

                // String

                writeString(this.senema, dos);

                // String

                writeString(this.sennumtelsen, dos);

                // String

                writeString(this.sendiplome, dos);

                // String

                writeString(this.sendecora, dos);

                // java.util.Date

                writeDate(this.sendatpreele, dos);

                // Long

                if (this.indid == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.indid);
                }

                // String

                writeString(this.sennomusecap, dos);

                // String

                writeString(this.senpagper, dos);

                // String

                writeString(this.senrngprtcod, dos);

                // String

                writeString(this.parpolorgcod, dos);

                // String

                writeString(this.senliedec, dos);

                // Long

                if (this.sendptnumnai == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sendptnumnai);
                }

                // Long

                if (this.sendptnumdec == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sendptnumdec);
                }

                // String

                writeString(this.senauttra, dos);

                // String

                writeString(this.senlog, dos);

                // String

                writeString(this.sencrinom, dos);

                // String

                writeString(this.senfem, dos);

                // String

                writeString(this.senautema, dos);

                // String

                writeString(this.senautgrpsen, dos);

                // String

                writeString(this.senautpagper, dos);

                // Long

                if (this.sennbrque == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sennbrque);
                }

                // String

                writeString(this.sennomdis, dos);

                // String

                writeString(this.numsen, dos);

                // Long

                if (this.sennumsie == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sennumsie);
                }

                // Long

                if (this.sennbrvid == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sennbrvid);
                }

                // String

                writeString(this.sennomdit, dos);

                // String

                writeString(this.titnobcod, dos);

                // Long

                if (this.senannnai == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senannnai);
                }

                // Long

                if (this.senmoinai == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senmoinai);
                }

                // Long

                if (this.senanndec == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senanndec);
                }

                // Long

                if (this.senmoidec == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senmoidec);
                }

                // String

                writeString(this.senobs4r1, dos);

                // String

                writeString(this.senobs4r2, dos);

                // String

                writeString(this.senobs3r1, dos);

                // String

                writeString(this.senobs3r2, dos);

                // java.util.Date

                writeDate(this.sendatderele, dos);

                // String

                writeString(this.catprocod4r, dos);

                // String

                writeString(this.catprocod3r, dos);

                // String

                writeString(this.sendespro4r, dos);

                // String

                writeString(this.sendespro3r, dos);

                // Long

                if (this.sencirnumcou4r == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sencirnumcou4r);
                }

                // String

                writeString(this.sencircou4r, dos);

                // Long

                if (this.sencirnumcou3r == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sencirnumcou3r);
                }

                // String

                writeString(this.sencircou3r, dos);

                // String

                writeString(this.sengrppolcodcou4r, dos);

                // String

                writeString(this.sentypappcou4r, dos);

                // java.util.Date

                writeDate(this.syscredat, dos);

                // String

                writeString(this.syscrelog, dos);

                // java.util.Date

                writeDate(this.sysmajdat, dos);

                // String

                writeString(this.sysmajlog, dos);

                // String

                writeString(this.autgrpsencod, dos);

                // String

                writeString(this.catprocod2e, dos);

                // String

                writeString(this.sendespro2e, dos);

                // String

                writeString(this.sendaiurl, dos);

                // String

                writeString(this.sengrppolcommu, dos);

                // String

                writeString(this.senobscommu, dos);

                // String

                writeString(this.senburcommu, dos);

                // String

                writeString(this.identifiant_rne, dos);

                // String

                writeString(this.identifiant_assnat, dos);

                // String

                writeString(this.titre_pair_france, dos);

                // String

                writeString(this.lister_titre_pair_france, dos);

                // String

                writeString(this.fonction_pair, dos);

                // String

                writeString(this.credit_portrait, dos);

                // String

                writeString(this.lister_autre_titre_pair_france, dos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void writeData(org.jboss.marshalling.Marshaller dos) {
            try {
                // String

                writeString(this.senmat, dos);

                // String

                writeString(this.quacod, dos);

                // String

                writeString(this.sennomuse, dos);

                // String

                writeString(this.sennompat, dos);

                // String

                writeString(this.sennommar, dos);

                // String

                writeString(this.sennomtec, dos);

                // String

                writeString(this.senprenomuse, dos);

                // String

                writeString(this.senprenomciv, dos);

                // java.util.Date

                writeDate(this.sendatnai, dos);

                // String

                writeString(this.senlienai, dos);

                // java.util.Date

                writeDate(this.sendatdec, dos);

                // String

                writeString(this.etasencod, dos);

                // String

                writeString(this.sendespro, dos);

                // String

                writeString(this.pcscod, dos);

                // String

                writeString(this.catprocod, dos);

                // Long

                if (this.senrngprt == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senrngprt);
                }

                // String

                writeString(this.sengrppolcodcou, dos);

                // String

                writeString(this.sengrppolliccou, dos);

                // String

                writeString(this.sentypappcou, dos);

                // String

                writeString(this.sencomcodcou, dos);

                // String

                writeString(this.sencomliccou, dos);

                // Long

                if (this.sencirnumcou == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sencirnumcou);
                }

                // String

                writeString(this.sencircou, dos);

                // String

                writeString(this.senburliccou, dos);

                // String

                writeString(this.senema, dos);

                // String

                writeString(this.sennumtelsen, dos);

                // String

                writeString(this.sendiplome, dos);

                // String

                writeString(this.sendecora, dos);

                // java.util.Date

                writeDate(this.sendatpreele, dos);

                // Long

                if (this.indid == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.indid);
                }

                // String

                writeString(this.sennomusecap, dos);

                // String

                writeString(this.senpagper, dos);

                // String

                writeString(this.senrngprtcod, dos);

                // String

                writeString(this.parpolorgcod, dos);

                // String

                writeString(this.senliedec, dos);

                // Long

                if (this.sendptnumnai == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sendptnumnai);
                }

                // Long

                if (this.sendptnumdec == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sendptnumdec);
                }

                // String

                writeString(this.senauttra, dos);

                // String

                writeString(this.senlog, dos);

                // String

                writeString(this.sencrinom, dos);

                // String

                writeString(this.senfem, dos);

                // String

                writeString(this.senautema, dos);

                // String

                writeString(this.senautgrpsen, dos);

                // String

                writeString(this.senautpagper, dos);

                // Long

                if (this.sennbrque == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sennbrque);
                }

                // String

                writeString(this.sennomdis, dos);

                // String

                writeString(this.numsen, dos);

                // Long

                if (this.sennumsie == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sennumsie);
                }

                // Long

                if (this.sennbrvid == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sennbrvid);
                }

                // String

                writeString(this.sennomdit, dos);

                // String

                writeString(this.titnobcod, dos);

                // Long

                if (this.senannnai == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senannnai);
                }

                // Long

                if (this.senmoinai == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senmoinai);
                }

                // Long

                if (this.senanndec == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senanndec);
                }

                // Long

                if (this.senmoidec == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.senmoidec);
                }

                // String

                writeString(this.senobs4r1, dos);

                // String

                writeString(this.senobs4r2, dos);

                // String

                writeString(this.senobs3r1, dos);

                // String

                writeString(this.senobs3r2, dos);

                // java.util.Date

                writeDate(this.sendatderele, dos);

                // String

                writeString(this.catprocod4r, dos);

                // String

                writeString(this.catprocod3r, dos);

                // String

                writeString(this.sendespro4r, dos);

                // String

                writeString(this.sendespro3r, dos);

                // Long

                if (this.sencirnumcou4r == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sencirnumcou4r);
                }

                // String

                writeString(this.sencircou4r, dos);

                // Long

                if (this.sencirnumcou3r == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sencirnumcou3r);
                }

                // String

                writeString(this.sencircou3r, dos);

                // String

                writeString(this.sengrppolcodcou4r, dos);

                // String

                writeString(this.sentypappcou4r, dos);

                // java.util.Date

                writeDate(this.syscredat, dos);

                // String

                writeString(this.syscrelog, dos);

                // java.util.Date

                writeDate(this.sysmajdat, dos);

                // String

                writeString(this.sysmajlog, dos);

                // String

                writeString(this.autgrpsencod, dos);

                // String

                writeString(this.catprocod2e, dos);

                // String

                writeString(this.sendespro2e, dos);

                // String

                writeString(this.sendaiurl, dos);

                // String

                writeString(this.sengrppolcommu, dos);

                // String

                writeString(this.senobscommu, dos);

                // String

                writeString(this.senburcommu, dos);

                // String

                writeString(this.identifiant_rne, dos);

                // String

                writeString(this.identifiant_assnat, dos);

                // String

                writeString(this.titre_pair_france, dos);

                // String

                writeString(this.lister_titre_pair_france, dos);

                // String

                writeString(this.fonction_pair, dos);

                // String

                writeString(this.credit_portrait, dos);

                // String

                writeString(this.lister_autre_titre_pair_france, dos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append("[");
            sb.append("senmat=" + senmat);
            sb.append(",quacod=" + quacod);
            sb.append(",sennomuse=" + sennomuse);
            sb.append(",sennompat=" + sennompat);
            sb.append(",sennommar=" + sennommar);
            sb.append(",sennomtec=" + sennomtec);
            sb.append(",senprenomuse=" + senprenomuse);
            sb.append(",senprenomciv=" + senprenomciv);
            sb.append(",sendatnai=" + String.valueOf(sendatnai));
            sb.append(",senlienai=" + senlienai);
            sb.append(",sendatdec=" + String.valueOf(sendatdec));
            sb.append(",etasencod=" + etasencod);
            sb.append(",sendespro=" + sendespro);
            sb.append(",pcscod=" + pcscod);
            sb.append(",catprocod=" + catprocod);
            sb.append(",senrngprt=" + String.valueOf(senrngprt));
            sb.append(",sengrppolcodcou=" + sengrppolcodcou);
            sb.append(",sengrppolliccou=" + sengrppolliccou);
            sb.append(",sentypappcou=" + sentypappcou);
            sb.append(",sencomcodcou=" + sencomcodcou);
            sb.append(",sencomliccou=" + sencomliccou);
            sb.append(",sencirnumcou=" + String.valueOf(sencirnumcou));
            sb.append(",sencircou=" + sencircou);
            sb.append(",senburliccou=" + senburliccou);
            sb.append(",senema=" + senema);
            sb.append(",sennumtelsen=" + sennumtelsen);
            sb.append(",sendiplome=" + sendiplome);
            sb.append(",sendecora=" + sendecora);
            sb.append(",sendatpreele=" + String.valueOf(sendatpreele));
            sb.append(",indid=" + String.valueOf(indid));
            sb.append(",sennomusecap=" + sennomusecap);
            sb.append(",senpagper=" + senpagper);
            sb.append(",senrngprtcod=" + senrngprtcod);
            sb.append(",parpolorgcod=" + parpolorgcod);
            sb.append(",senliedec=" + senliedec);
            sb.append(",sendptnumnai=" + String.valueOf(sendptnumnai));
            sb.append(",sendptnumdec=" + String.valueOf(sendptnumdec));
            sb.append(",senauttra=" + senauttra);
            sb.append(",senlog=" + senlog);
            sb.append(",sencrinom=" + sencrinom);
            sb.append(",senfem=" + senfem);
            sb.append(",senautema=" + senautema);
            sb.append(",senautgrpsen=" + senautgrpsen);
            sb.append(",senautpagper=" + senautpagper);
            sb.append(",sennbrque=" + String.valueOf(sennbrque));
            sb.append(",sennomdis=" + sennomdis);
            sb.append(",numsen=" + numsen);
            sb.append(",sennumsie=" + String.valueOf(sennumsie));
            sb.append(",sennbrvid=" + String.valueOf(sennbrvid));
            sb.append(",sennomdit=" + sennomdit);
            sb.append(",titnobcod=" + titnobcod);
            sb.append(",senannnai=" + String.valueOf(senannnai));
            sb.append(",senmoinai=" + String.valueOf(senmoinai));
            sb.append(",senanndec=" + String.valueOf(senanndec));
            sb.append(",senmoidec=" + String.valueOf(senmoidec));
            sb.append(",senobs4r1=" + senobs4r1);
            sb.append(",senobs4r2=" + senobs4r2);
            sb.append(",senobs3r1=" + senobs3r1);
            sb.append(",senobs3r2=" + senobs3r2);
            sb.append(",sendatderele=" + String.valueOf(sendatderele));
            sb.append(",catprocod4r=" + catprocod4r);
            sb.append(",catprocod3r=" + catprocod3r);
            sb.append(",sendespro4r=" + sendespro4r);
            sb.append(",sendespro3r=" + sendespro3r);
            sb.append(",sencirnumcou4r=" + String.valueOf(sencirnumcou4r));
            sb.append(",sencircou4r=" + sencircou4r);
            sb.append(",sencirnumcou3r=" + String.valueOf(sencirnumcou3r));
            sb.append(",sencircou3r=" + sencircou3r);
            sb.append(",sengrppolcodcou4r=" + sengrppolcodcou4r);
            sb.append(",sentypappcou4r=" + sentypappcou4r);
            sb.append(",syscredat=" + String.valueOf(syscredat));
            sb.append(",syscrelog=" + syscrelog);
            sb.append(",sysmajdat=" + String.valueOf(sysmajdat));
            sb.append(",sysmajlog=" + sysmajlog);
            sb.append(",autgrpsencod=" + autgrpsencod);
            sb.append(",catprocod2e=" + catprocod2e);
            sb.append(",sendespro2e=" + sendespro2e);
            sb.append(",sendaiurl=" + sendaiurl);
            sb.append(",sengrppolcommu=" + sengrppolcommu);
            sb.append(",senobscommu=" + senobscommu);
            sb.append(",senburcommu=" + senburcommu);
            sb.append(",identifiant_rne=" + identifiant_rne);
            sb.append(",identifiant_assnat=" + identifiant_assnat);
            sb.append(",titre_pair_france=" + titre_pair_france);
            sb.append(",lister_titre_pair_france=" + lister_titre_pair_france);
            sb.append(",fonction_pair=" + fonction_pair);
            sb.append(",credit_portrait=" + credit_portrait);
            sb.append(",lister_autre_titre_pair_france=" + lister_autre_titre_pair_france);
            sb.append("]");

            return sb.toString();
        }

        /**
         * Compare keys
         */
        public int compareTo(row2Struct other) {
            int returnValue = -1;

            return returnValue;
        }

        private int checkNullsAndCompare(Object object1, Object object2) {
            int returnValue = 0;
            if (object1 instanceof Comparable && object2 instanceof Comparable) {
                returnValue = ((Comparable) object1).compareTo(object2);
            } else if (object1 != null && object2 != null) {
                returnValue = compareStrings(object1.toString(), object2.toString());
            } else if (object1 == null && object2 != null) {
                returnValue = 1;
            } else if (object1 != null && object2 == null) {
                returnValue = -1;
            } else {
                returnValue = 0;
            }

            return returnValue;
        }

        private int compareStrings(String string1, String string2) {
            return string1.compareTo(string2);
        }
    }

    public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {

        static final byte[] commonByteArrayLock_IRSEN_Test_delete = new byte[0];
        static byte[] commonByteArray_IRSEN_Test_delete = new byte[0];

        public String SENMAT;

        public String getSENMAT() {
            return this.SENMAT;
        }

        public String QUACOD;

        public String getQUACOD() {
            return this.QUACOD;
        }

        public String SENNOMUSE;

        public String getSENNOMUSE() {
            return this.SENNOMUSE;
        }

        public String SENNOMPAT;

        public String getSENNOMPAT() {
            return this.SENNOMPAT;
        }

        public String SENNOMMAR;

        public String getSENNOMMAR() {
            return this.SENNOMMAR;
        }

        public String SENNOMTEC;

        public String getSENNOMTEC() {
            return this.SENNOMTEC;
        }

        public String SENPRENOMUSE;

        public String getSENPRENOMUSE() {
            return this.SENPRENOMUSE;
        }

        public String SENPRENOMCIV;

        public String getSENPRENOMCIV() {
            return this.SENPRENOMCIV;
        }

        public String SENDATNAI;

        public String getSENDATNAI() {
            return this.SENDATNAI;
        }

        public String SENLIENAI;

        public String getSENLIENAI() {
            return this.SENLIENAI;
        }

        public String SENDESPRO;

        public String getSENDESPRO() {
            return this.SENDESPRO;
        }

        public String PCSCOD42;

        public String getPCSCOD42() {
            return this.PCSCOD42;
        }

        public String CATPROCOD;

        public String getCATPROCOD() {
            return this.CATPROCOD;
        }

        public String SENEMA;

        public String getSENEMA() {
            return this.SENEMA;
        }

        public String SENNUMTELSEN;

        public String getSENNUMTELSEN() {
            return this.SENNUMTELSEN;
        }

        public String SENDIPLOME;

        public String getSENDIPLOME() {
            return this.SENDIPLOME;
        }

        public String SENDECORA;

        public String getSENDECORA() {
            return this.SENDECORA;
        }

        public String SENNOMUSECAP;

        public String getSENNOMUSECAP() {
            return this.SENNOMUSECAP;
        }

        public String SENDPTNUMNAI;

        public String getSENDPTNUMNAI() {
            return this.SENDPTNUMNAI;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Test_delete.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Test_delete.length == 0) {
                        commonByteArray_IRSEN_Test_delete = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Test_delete = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Test_delete, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Test_delete, 0, length, utf8Charset);
            }
            return strReturn;
        }

        private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            String strReturn = null;
            int length = 0;
            length = unmarshaller.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Test_delete.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Test_delete.length == 0) {
                        commonByteArray_IRSEN_Test_delete = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Test_delete = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Test_delete, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Test_delete, 0, length, utf8Charset);
            }
            return strReturn;
        }

        private void writeString(String str, ObjectOutputStream dos) throws IOException {
            if (str == null) {
                dos.writeInt(-1);
            } else {
                byte[] byteArray = str.getBytes(utf8Charset);
                dos.writeInt(byteArray.length);
                dos.write(byteArray);
            }
        }

        private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (str == null) {
                marshaller.writeInt(-1);
            } else {
                byte[] byteArray = str.getBytes(utf8Charset);
                marshaller.writeInt(byteArray.length);
                marshaller.write(byteArray);
            }
        }

        public void readData(ObjectInputStream dis) {
            synchronized (commonByteArrayLock_IRSEN_Test_delete) {
                try {
                    int length = 0;

                    this.SENMAT = readString(dis);

                    this.QUACOD = readString(dis);

                    this.SENNOMUSE = readString(dis);

                    this.SENNOMPAT = readString(dis);

                    this.SENNOMMAR = readString(dis);

                    this.SENNOMTEC = readString(dis);

                    this.SENPRENOMUSE = readString(dis);

                    this.SENPRENOMCIV = readString(dis);

                    this.SENDATNAI = readString(dis);

                    this.SENLIENAI = readString(dis);

                    this.SENDESPRO = readString(dis);

                    this.PCSCOD42 = readString(dis);

                    this.CATPROCOD = readString(dis);

                    this.SENEMA = readString(dis);

                    this.SENNUMTELSEN = readString(dis);

                    this.SENDIPLOME = readString(dis);

                    this.SENDECORA = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    this.SENDPTNUMNAI = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Test_delete) {
                try {
                    int length = 0;

                    this.SENMAT = readString(dis);

                    this.QUACOD = readString(dis);

                    this.SENNOMUSE = readString(dis);

                    this.SENNOMPAT = readString(dis);

                    this.SENNOMMAR = readString(dis);

                    this.SENNOMTEC = readString(dis);

                    this.SENPRENOMUSE = readString(dis);

                    this.SENPRENOMCIV = readString(dis);

                    this.SENDATNAI = readString(dis);

                    this.SENLIENAI = readString(dis);

                    this.SENDESPRO = readString(dis);

                    this.PCSCOD42 = readString(dis);

                    this.CATPROCOD = readString(dis);

                    this.SENEMA = readString(dis);

                    this.SENNUMTELSEN = readString(dis);

                    this.SENDIPLOME = readString(dis);

                    this.SENDECORA = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    this.SENDPTNUMNAI = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void writeData(ObjectOutputStream dos) {
            try {
                // String

                writeString(this.SENMAT, dos);

                // String

                writeString(this.QUACOD, dos);

                // String

                writeString(this.SENNOMUSE, dos);

                // String

                writeString(this.SENNOMPAT, dos);

                // String

                writeString(this.SENNOMMAR, dos);

                // String

                writeString(this.SENNOMTEC, dos);

                // String

                writeString(this.SENPRENOMUSE, dos);

                // String

                writeString(this.SENPRENOMCIV, dos);

                // String

                writeString(this.SENDATNAI, dos);

                // String

                writeString(this.SENLIENAI, dos);

                // String

                writeString(this.SENDESPRO, dos);

                // String

                writeString(this.PCSCOD42, dos);

                // String

                writeString(this.CATPROCOD, dos);

                // String

                writeString(this.SENEMA, dos);

                // String

                writeString(this.SENNUMTELSEN, dos);

                // String

                writeString(this.SENDIPLOME, dos);

                // String

                writeString(this.SENDECORA, dos);

                // String

                writeString(this.SENNOMUSECAP, dos);

                // String

                writeString(this.SENDPTNUMNAI, dos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void writeData(org.jboss.marshalling.Marshaller dos) {
            try {
                // String

                writeString(this.SENMAT, dos);

                // String

                writeString(this.QUACOD, dos);

                // String

                writeString(this.SENNOMUSE, dos);

                // String

                writeString(this.SENNOMPAT, dos);

                // String

                writeString(this.SENNOMMAR, dos);

                // String

                writeString(this.SENNOMTEC, dos);

                // String

                writeString(this.SENPRENOMUSE, dos);

                // String

                writeString(this.SENPRENOMCIV, dos);

                // String

                writeString(this.SENDATNAI, dos);

                // String

                writeString(this.SENLIENAI, dos);

                // String

                writeString(this.SENDESPRO, dos);

                // String

                writeString(this.PCSCOD42, dos);

                // String

                writeString(this.CATPROCOD, dos);

                // String

                writeString(this.SENEMA, dos);

                // String

                writeString(this.SENNUMTELSEN, dos);

                // String

                writeString(this.SENDIPLOME, dos);

                // String

                writeString(this.SENDECORA, dos);

                // String

                writeString(this.SENNOMUSECAP, dos);

                // String

                writeString(this.SENDPTNUMNAI, dos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append("[");
            sb.append("SENMAT=" + SENMAT);
            sb.append(",QUACOD=" + QUACOD);
            sb.append(",SENNOMUSE=" + SENNOMUSE);
            sb.append(",SENNOMPAT=" + SENNOMPAT);
            sb.append(",SENNOMMAR=" + SENNOMMAR);
            sb.append(",SENNOMTEC=" + SENNOMTEC);
            sb.append(",SENPRENOMUSE=" + SENPRENOMUSE);
            sb.append(",SENPRENOMCIV=" + SENPRENOMCIV);
            sb.append(",SENDATNAI=" + SENDATNAI);
            sb.append(",SENLIENAI=" + SENLIENAI);
            sb.append(",SENDESPRO=" + SENDESPRO);
            sb.append(",PCSCOD42=" + PCSCOD42);
            sb.append(",CATPROCOD=" + CATPROCOD);
            sb.append(",SENEMA=" + SENEMA);
            sb.append(",SENNUMTELSEN=" + SENNUMTELSEN);
            sb.append(",SENDIPLOME=" + SENDIPLOME);
            sb.append(",SENDECORA=" + SENDECORA);
            sb.append(",SENNOMUSECAP=" + SENNOMUSECAP);
            sb.append(",SENDPTNUMNAI=" + SENDPTNUMNAI);
            sb.append("]");

            return sb.toString();
        }

        /**
         * Compare keys
         */
        public int compareTo(row1Struct other) {
            int returnValue = -1;

            return returnValue;
        }

        private int checkNullsAndCompare(Object object1, Object object2) {
            int returnValue = 0;
            if (object1 instanceof Comparable && object2 instanceof Comparable) {
                returnValue = ((Comparable) object1).compareTo(object2);
            } else if (object1 != null && object2 != null) {
                returnValue = compareStrings(object1.toString(), object2.toString());
            } else if (object1 == null && object2 != null) {
                returnValue = 1;
            } else if (object1 != null && object2 == null) {
                returnValue = -1;
            } else {
                returnValue = 0;
            }

            return returnValue;
        }

        private int compareStrings(String string1, String string2) {
            return string1.compareTo(string2);
        }
    }

    public void tDBRow_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
        globalMap.put("tDBRow_1_SUBPROCESS_STATE", 0);

        final boolean execStat = this.execStat;

        String iterateId = "";

        String currentComponent = "";
        java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

        try {
            // TDI-39566 avoid throwing an useless Exception
            boolean resumeIt = true;
            if (globalResumeTicket == false && resumeEntryMethodName != null) {
                String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { // start the resume
                globalResumeTicket = true;

                row1Struct row1 = new row1Struct();
                row2Struct row2 = new row2Struct();

                /**
                 * [tFlowToIterate_1 begin ] start
                 */

                int NB_ITERATE_tDBRow_2 = 0; // for statistics

                ok_Hash.put("tFlowToIterate_1", false);
                start_Hash.put("tFlowToIterate_1", System.currentTimeMillis());

                currentComponent = "tFlowToIterate_1";

                if (execStat) {
                    runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
                }

                int tos_count_tFlowToIterate_1 = 0;

                int nb_line_tFlowToIterate_1 = 0;
                int counter_tFlowToIterate_1 = 0;

                /**
                 * [tFlowToIterate_1 begin ] stop
                 */

                /**
                 * [tDBRow_1 begin ] start
                 */

                ok_Hash.put("tDBRow_1", false);
                start_Hash.put("tDBRow_1", System.currentTimeMillis());

                currentComponent = "tDBRow_1";

                int tos_count_tDBRow_1 = 0;

                java.sql.Connection conn_tDBRow_1 = null;
                String query_tDBRow_1 = "";
                boolean whetherReject_tDBRow_1 = false;
                String driverClass_tDBRow_1 = "org.postgresql.Driver";
                java.lang.Class jdbcclazz_tDBRow_1 = java.lang.Class.forName(driverClass_tDBRow_1);

                String url_tDBRow_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "irsen";

                String dbUser_tDBRow_1 = "postgres";

                final String decryptedPassword_tDBRow_1 = routines.system.PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:to0mKLzAfQ3wIfGmFcvxPDpeb9r5lrf3QgfvDg=="
                );

                String dbPwd_tDBRow_1 = decryptedPassword_tDBRow_1;

                conn_tDBRow_1 = java.sql.DriverManager.getConnection(url_tDBRow_1, dbUser_tDBRow_1, dbPwd_tDBRow_1);

                resourceMap.put("conn_tDBRow_1", conn_tDBRow_1);
                if (conn_tDBRow_1.getAutoCommit()) {
                    conn_tDBRow_1.setAutoCommit(false);
                }
                int commitEvery_tDBRow_1 = 10000;
                int commitCounter_tDBRow_1 = 0;

                java.sql.Statement stmt_tDBRow_1 = conn_tDBRow_1.createStatement();
                resourceMap.put("stmt_tDBRow_1", stmt_tDBRow_1);

                /**
                 * [tDBRow_1 begin ] stop
                 */

                /**
                 * [tDBRow_1 main ] start
                 */

                currentComponent = "tDBRow_1";

                row1 = null;

                query_tDBRow_1 = "select * from sen";
                whetherReject_tDBRow_1 = false;
                globalMap.put("tDBRow_1_QUERY", query_tDBRow_1);
                try {
                    stmt_tDBRow_1.execute(query_tDBRow_1);
                } catch (java.lang.Exception e) {
                    whetherReject_tDBRow_1 = true;

                    System.err.print(e.getMessage());
                    globalMap.put("tDBRow_1_ERROR_MESSAGE", e.getMessage());
                }

                if (!whetherReject_tDBRow_1) {
                    row1 = new row1Struct();
                }

                commitCounter_tDBRow_1++;
                if (commitEvery_tDBRow_1 <= commitCounter_tDBRow_1) {
                    conn_tDBRow_1.commit();

                    commitCounter_tDBRow_1 = 0;
                }

                tos_count_tDBRow_1++;

                /**
                 * [tDBRow_1 main ] stop
                 */

                /**
                 * [tDBRow_1 process_data_begin ] start
                 */

                currentComponent = "tDBRow_1";

                /**
                 * [tDBRow_1 process_data_begin ] stop
                 */
                // Start of branch "row1"
                if (row1 != null) {
                    /**
                     * [tFlowToIterate_1 main ] start
                     */

                    currentComponent = "tFlowToIterate_1";

                    if (execStat) {
                        runStat.updateStatOnConnection(iterateId, 1, 1, "row1");
                    }

                    globalMap.put("matricule", row1.SENMAT);
                    nb_line_tFlowToIterate_1++;
                    counter_tFlowToIterate_1++;
                    globalMap.put("tFlowToIterate_1_CURRENT_ITERATION", counter_tFlowToIterate_1);

                    tos_count_tFlowToIterate_1++;

                    /**
                     * [tFlowToIterate_1 main ] stop
                     */

                    /**
                     * [tFlowToIterate_1 process_data_begin ] start
                     */

                    currentComponent = "tFlowToIterate_1";

                    /**
                     * [tFlowToIterate_1 process_data_begin ] stop
                     */
                    NB_ITERATE_tDBRow_2++;

                    if (execStat) {
                        runStat.updateStatOnConnection("row2", 3, 0);
                    }

                    if (execStat) {
                        runStat.updateStatOnConnection("iterate1", 1, "exec" + NB_ITERATE_tDBRow_2);
                        // Thread.sleep(1000);
                    }

                    /**
                     * [tLogRow_1 begin ] start
                     */

                    ok_Hash.put("tLogRow_1", false);
                    start_Hash.put("tLogRow_1", System.currentTimeMillis());

                    currentComponent = "tLogRow_1";

                    if (execStat) {
                        runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
                    }

                    int tos_count_tLogRow_1 = 0;

                    ///////////////////////

                    final String OUTPUT_FIELD_SEPARATOR_tLogRow_1 = "|";
                    java.io.PrintStream consoleOut_tLogRow_1 = null;

                    StringBuilder strBuffer_tLogRow_1 = null;
                    int nb_line_tLogRow_1 = 0;
                    ///////////////////////

                    /**
                     * [tLogRow_1 begin ] stop
                     */

                    /**
                     * [tDBRow_2 begin ] start
                     */

                    ok_Hash.put("tDBRow_2", false);
                    start_Hash.put("tDBRow_2", System.currentTimeMillis());

                    currentComponent = "tDBRow_2";

                    int tos_count_tDBRow_2 = 0;

                    java.sql.Connection conn_tDBRow_2 = null;
                    String query_tDBRow_2 = "";
                    boolean whetherReject_tDBRow_2 = false;
                    String driverClass_tDBRow_2 = "org.postgresql.Driver";
                    java.lang.Class jdbcclazz_tDBRow_2 = java.lang.Class.forName(driverClass_tDBRow_2);

                    String url_tDBRow_2 = "jdbc:postgresql://" + "brandy-test.senat.fr" + ":" + "5603" + "/" + "sendevrep2";

                    String dbUser_tDBRow_2 = "pgdba";

                    final String decryptedPassword_tDBRow_2 = routines.system.PasswordEncryptUtil.decryptPassword(
                        "enc:routine.encryption.key.v1:0I2RzZSDk72q2Md2HgLCM+r8iFEOMKmMbe7rpSfKoCT6J5ltWu9YAg=="
                    );

                    String dbPwd_tDBRow_2 = decryptedPassword_tDBRow_2;

                    conn_tDBRow_2 = java.sql.DriverManager.getConnection(url_tDBRow_2, dbUser_tDBRow_2, dbPwd_tDBRow_2);

                    resourceMap.put("conn_tDBRow_2", conn_tDBRow_2);
                    if (conn_tDBRow_2.getAutoCommit()) {
                        conn_tDBRow_2.setAutoCommit(false);
                    }
                    int commitEvery_tDBRow_2 = 10000;
                    int commitCounter_tDBRow_2 = 0;

                    java.sql.Statement stmt_tDBRow_2 = conn_tDBRow_2.createStatement();
                    resourceMap.put("stmt_tDBRow_2", stmt_tDBRow_2);

                    /**
                     * [tDBRow_2 begin ] stop
                     */

                    /**
                     * [tDBRow_2 main ] start
                     */

                    currentComponent = "tDBRow_2";

                    row2 = null;

                    query_tDBRow_2 = "Select * from senateurs.sen where senmat='" + ((String) globalMap.get("matricule")) + "'";
                    whetherReject_tDBRow_2 = false;
                    globalMap.put("tDBRow_2_QUERY", query_tDBRow_2);
                    try {
                        stmt_tDBRow_2.execute(query_tDBRow_2);
                    } catch (java.lang.Exception e) {
                        whetherReject_tDBRow_2 = true;

                        System.err.print(e.getMessage());
                        globalMap.put("tDBRow_2_ERROR_MESSAGE", e.getMessage());
                    }

                    if (!whetherReject_tDBRow_2) {
                        row2 = new row2Struct();
                    }

                    commitCounter_tDBRow_2++;
                    if (commitEvery_tDBRow_2 <= commitCounter_tDBRow_2) {
                        conn_tDBRow_2.commit();

                        commitCounter_tDBRow_2 = 0;
                    }

                    tos_count_tDBRow_2++;

                    /**
                     * [tDBRow_2 main ] stop
                     */

                    /**
                     * [tDBRow_2 process_data_begin ] start
                     */

                    currentComponent = "tDBRow_2";

                    /**
                     * [tDBRow_2 process_data_begin ] stop
                     */
                    // Start of branch "row2"
                    if (row2 != null) {
                        /**
                         * [tLogRow_1 main ] start
                         */

                        currentComponent = "tLogRow_1";

                        if (execStat) {
                            runStat.updateStatOnConnection(iterateId, 1, 1, "row2");
                        }

                        ///////////////////////

                        strBuffer_tLogRow_1 = new StringBuilder();

                        if (row2.senmat != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senmat));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.quacod != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.quacod));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennomuse != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennomuse));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennompat != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennompat));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennommar != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennommar));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennomtec != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennomtec));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senprenomuse != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senprenomuse));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senprenomciv != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senprenomciv));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendatnai != null) { //
                            strBuffer_tLogRow_1.append(FormatterUtils.format_Date(row2.sendatnai, "dd-MM-yyyy"));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senlienai != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senlienai));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendatdec != null) { //
                            strBuffer_tLogRow_1.append(FormatterUtils.format_Date(row2.sendatdec, "dd-MM-yyyy"));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.etasencod != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.etasencod));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendespro != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendespro));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.pcscod != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.pcscod));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.catprocod != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.catprocod));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senrngprt != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senrngprt));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sengrppolcodcou != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sengrppolcodcou));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sengrppolliccou != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sengrppolliccou));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sentypappcou != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sentypappcou));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencomcodcou != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencomcodcou));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencomliccou != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencomliccou));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencirnumcou != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencirnumcou));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencircou != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencircou));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senburliccou != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senburliccou));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senema != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senema));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennumtelsen != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennumtelsen));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendiplome != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendiplome));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendecora != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendecora));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendatpreele != null) { //
                            strBuffer_tLogRow_1.append(FormatterUtils.format_Date(row2.sendatpreele, "dd-MM-yyyy"));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.indid != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.indid));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennomusecap != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennomusecap));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senpagper != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senpagper));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senrngprtcod != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senrngprtcod));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.parpolorgcod != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.parpolorgcod));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senliedec != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senliedec));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendptnumnai != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendptnumnai));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendptnumdec != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendptnumdec));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senauttra != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senauttra));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senlog != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senlog));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencrinom != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencrinom));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senfem != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senfem));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senautema != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senautema));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senautgrpsen != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senautgrpsen));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senautpagper != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senautpagper));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennbrque != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennbrque));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennomdis != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennomdis));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.numsen != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.numsen));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennumsie != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennumsie));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennbrvid != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennbrvid));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sennomdit != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sennomdit));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.titnobcod != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.titnobcod));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senannnai != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senannnai));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senmoinai != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senmoinai));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senanndec != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senanndec));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senmoidec != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senmoidec));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senobs4r1 != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senobs4r1));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senobs4r2 != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senobs4r2));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senobs3r1 != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senobs3r1));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senobs3r2 != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senobs3r2));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendatderele != null) { //
                            strBuffer_tLogRow_1.append(FormatterUtils.format_Date(row2.sendatderele, "dd-MM-yyyy"));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.catprocod4r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.catprocod4r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.catprocod3r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.catprocod3r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendespro4r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendespro4r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendespro3r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendespro3r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencirnumcou4r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencirnumcou4r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencircou4r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencircou4r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencirnumcou3r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencirnumcou3r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sencircou3r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sencircou3r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sengrppolcodcou4r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sengrppolcodcou4r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sentypappcou4r != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sentypappcou4r));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.syscredat != null) { //
                            strBuffer_tLogRow_1.append(FormatterUtils.format_Date(row2.syscredat, "dd-MM-yyyy"));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.syscrelog != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.syscrelog));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sysmajdat != null) { //
                            strBuffer_tLogRow_1.append(FormatterUtils.format_Date(row2.sysmajdat, "dd-MM-yyyy"));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sysmajlog != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sysmajlog));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.autgrpsencod != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.autgrpsencod));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.catprocod2e != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.catprocod2e));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendespro2e != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendespro2e));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sendaiurl != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sendaiurl));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.sengrppolcommu != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.sengrppolcommu));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senobscommu != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senobscommu));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.senburcommu != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.senburcommu));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.identifiant_rne != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.identifiant_rne));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.identifiant_assnat != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.identifiant_assnat));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.titre_pair_france != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.titre_pair_france));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.lister_titre_pair_france != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.lister_titre_pair_france));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.fonction_pair != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.fonction_pair));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.credit_portrait != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.credit_portrait));
                        } //

                        strBuffer_tLogRow_1.append("|");

                        if (row2.lister_autre_titre_pair_france != null) { //
                            strBuffer_tLogRow_1.append(String.valueOf(row2.lister_autre_titre_pair_france));
                        } //

                        if (globalMap.get("tLogRow_CONSOLE") != null) {
                            consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
                        } else {
                            consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
                            globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
                        }
                        consoleOut_tLogRow_1.println(strBuffer_tLogRow_1.toString());
                        consoleOut_tLogRow_1.flush();
                        nb_line_tLogRow_1++;
                        //////

                        //////

                        ///////////////////////

                        tos_count_tLogRow_1++;

                        /**
                         * [tLogRow_1 main ] stop
                         */

                        /**
                         * [tLogRow_1 process_data_begin ] start
                         */

                        currentComponent = "tLogRow_1";

                        /**
                         * [tLogRow_1 process_data_begin ] stop
                         */

                        /**
                         * [tLogRow_1 process_data_end ] start
                         */

                        currentComponent = "tLogRow_1";
                        /**
                         * [tLogRow_1 process_data_end ] stop
                         */

                    } // End of branch "row2"

                    /**
                     * [tDBRow_2 process_data_end ] start
                     */

                    currentComponent = "tDBRow_2";

                    /**
                     * [tDBRow_2 process_data_end ] stop
                     */

                    /**
                     * [tDBRow_2 end ] start
                     */

                    currentComponent = "tDBRow_2";

                    stmt_tDBRow_2.close();
                    resourceMap.remove("stmt_tDBRow_2");
                    resourceMap.put("statementClosed_tDBRow_2", true);
                    if (commitEvery_tDBRow_2 > commitCounter_tDBRow_2) {
                        conn_tDBRow_2.commit();

                        commitCounter_tDBRow_2 = 0;
                    }
                    conn_tDBRow_2.close();

                    if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) && routines.system.BundleUtils.inOSGi()) {
                        Class
                            .forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
                            .getMethod("checkedShutdown")
                            .invoke(null, (Object[]) null);
                    }

                    resourceMap.put("finish_tDBRow_2", true);

                    ok_Hash.put("tDBRow_2", true);
                    end_Hash.put("tDBRow_2", System.currentTimeMillis());

                    /**
                     * [tDBRow_2 end ] stop
                     */

                    /**
                     * [tLogRow_1 end ] start
                     */

                    currentComponent = "tLogRow_1";

                    //////
                    //////
                    globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);

                    ///////////////////////

                    if (execStat) {
                        runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
                    }

                    ok_Hash.put("tLogRow_1", true);
                    end_Hash.put("tLogRow_1", System.currentTimeMillis());

                    /**
                     * [tLogRow_1 end ] stop
                     */

                    if (execStat) {
                        runStat.updateStatOnConnection("iterate1", 2, "exec" + NB_ITERATE_tDBRow_2);
                    }

                    /**
                     * [tFlowToIterate_1 process_data_end ] start
                     */

                    currentComponent = "tFlowToIterate_1";
                    /**
                     * [tFlowToIterate_1 process_data_end ] stop
                     */

                } // End of branch "row1"

                /**
                 * [tDBRow_1 process_data_end ] start
                 */

                currentComponent = "tDBRow_1";

                /**
                 * [tDBRow_1 process_data_end ] stop
                 */

                /**
                 * [tDBRow_1 end ] start
                 */

                currentComponent = "tDBRow_1";

                stmt_tDBRow_1.close();
                resourceMap.remove("stmt_tDBRow_1");
                resourceMap.put("statementClosed_tDBRow_1", true);
                if (commitEvery_tDBRow_1 > commitCounter_tDBRow_1) {
                    conn_tDBRow_1.commit();

                    commitCounter_tDBRow_1 = 0;
                }
                conn_tDBRow_1.close();

                if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) && routines.system.BundleUtils.inOSGi()) {
                    Class
                        .forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
                        .getMethod("checkedShutdown")
                        .invoke(null, (Object[]) null);
                }

                resourceMap.put("finish_tDBRow_1", true);

                ok_Hash.put("tDBRow_1", true);
                end_Hash.put("tDBRow_1", System.currentTimeMillis());

                /**
                 * [tDBRow_1 end ] stop
                 */

                /**
                 * [tFlowToIterate_1 end ] start
                 */

                currentComponent = "tFlowToIterate_1";

                globalMap.put("tFlowToIterate_1_NB_LINE", nb_line_tFlowToIterate_1);
                if (execStat) {
                    runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
                }

                ok_Hash.put("tFlowToIterate_1", true);
                end_Hash.put("tFlowToIterate_1", System.currentTimeMillis());
                /**
                 * [tFlowToIterate_1 end ] stop
                 */

            } // end the resume
        } catch (java.lang.Exception e) {
            TalendException te = new TalendException(e, currentComponent, globalMap);

            throw te;
        } catch (java.lang.Error error) {
            runStat.stopThreadStat();

            throw error;
        } finally {
            try {
                /**
                 * [tDBRow_1 finally ] start
                 */

                currentComponent = "tDBRow_1";

                try {
                    if (resourceMap.get("statementClosed_tDBRow_1") == null) {
                        java.sql.Statement stmtToClose_tDBRow_1 = null;
                        if ((stmtToClose_tDBRow_1 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_1")) != null) {
                            stmtToClose_tDBRow_1.close();
                        }
                    }
                } finally {
                    if (resourceMap.get("finish_tDBRow_1") == null) {
                        java.sql.Connection ctn_tDBRow_1 = null;
                        if ((ctn_tDBRow_1 = (java.sql.Connection) resourceMap.get("conn_tDBRow_1")) != null) {
                            try {
                                ctn_tDBRow_1.close();
                            } catch (java.sql.SQLException sqlEx_tDBRow_1) {
                                String errorMessage_tDBRow_1 = "failed to close the connection in tDBRow_1 :" + sqlEx_tDBRow_1.getMessage();
                                System.err.println(errorMessage_tDBRow_1);
                            }
                        }
                    }
                }

                /**
                 * [tDBRow_1 finally ] stop
                 */

                /**
                 * [tFlowToIterate_1 finally ] start
                 */

                currentComponent = "tFlowToIterate_1";

                /**
                 * [tFlowToIterate_1 finally ] stop
                 */

                /**
                 * [tDBRow_2 finally ] start
                 */

                currentComponent = "tDBRow_2";

                try {
                    if (resourceMap.get("statementClosed_tDBRow_2") == null) {
                        java.sql.Statement stmtToClose_tDBRow_2 = null;
                        if ((stmtToClose_tDBRow_2 = (java.sql.Statement) resourceMap.remove("stmt_tDBRow_2")) != null) {
                            stmtToClose_tDBRow_2.close();
                        }
                    }
                } finally {
                    if (resourceMap.get("finish_tDBRow_2") == null) {
                        java.sql.Connection ctn_tDBRow_2 = null;
                        if ((ctn_tDBRow_2 = (java.sql.Connection) resourceMap.get("conn_tDBRow_2")) != null) {
                            try {
                                ctn_tDBRow_2.close();
                            } catch (java.sql.SQLException sqlEx_tDBRow_2) {
                                String errorMessage_tDBRow_2 = "failed to close the connection in tDBRow_2 :" + sqlEx_tDBRow_2.getMessage();
                                System.err.println(errorMessage_tDBRow_2);
                            }
                        }
                    }
                }

                /**
                 * [tDBRow_2 finally ] stop
                 */

                /**
                 * [tLogRow_1 finally ] start
                 */

                currentComponent = "tLogRow_1";
                /**
                 * [tLogRow_1 finally ] stop
                 */

            } catch (java.lang.Exception e) {
                // ignore
            } catch (java.lang.Error error) {
                // ignore
            }
            resourceMap = null;
        }

        globalMap.put("tDBRow_1_SUBPROCESS_STATE", 1);
    }

    public String resuming_logs_dir_path = null;
    public String resuming_checkpoint_path = null;
    public String parent_part_launcher = null;
    private String resumeEntryMethodName = null;
    private boolean globalResumeTicket = false;

    public boolean watch = false;
    // portStats is null, it means don't execute the statistics
    public Integer portStats = null;
    public int portTraces = 4334;
    public String clientHost;
    public String defaultClientHost = "localhost";
    public String contextStr = "Default";
    public boolean isDefaultContext = true;
    public String pid = "0";
    public String rootPid = null;
    public String fatherPid = null;
    public String fatherNode = null;
    public long startTime = 0;
    public boolean isChildJob = false;
    public String log4jLevel = "";

    private boolean enableLogStash;

    private boolean execStat = true;

    private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
        protected java.util.Map<String, String> initialValue() {
            java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
            threadRunResultMap.put("errorCode", null);
            threadRunResultMap.put("status", "");
            return threadRunResultMap;
        }
    };

    protected PropertiesWithType context_param = new PropertiesWithType();
    public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

    public String status = "";

    public static void main(String[] args) {
        final Test_delete Test_deleteClass = new Test_delete();

        int exitCode = Test_deleteClass.runJobInTOS(args);

        System.exit(exitCode);
    }

    public String[][] runJob(String[] args) {
        int exitCode = runJobInTOS(args);
        String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

        return bufferValue;
    }

    public boolean hastBufferOutputComponent() {
        boolean hastBufferOutput = false;

        return hastBufferOutput;
    }

    public int runJobInTOS(String[] args) {
        // reset status
        status = "";

        String lastStr = "";
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--context_param")) {
                lastStr = arg;
            } else if (lastStr.equals("")) {
                evalParam(arg);
            } else {
                evalParam(lastStr + " " + arg);
                lastStr = "";
            }
        }
        enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

        if (clientHost == null) {
            clientHost = defaultClientHost;
        }

        if (pid == null || "0".equals(pid)) {
            pid = TalendString.getAsciiRandomString(6);
        }

        if (rootPid == null) {
            rootPid = pid;
        }
        if (fatherPid == null) {
            fatherPid = pid;
        } else {
            isChildJob = true;
        }

        if (portStats != null) {
            // portStats = -1; //for testing
            if (portStats < 0 || portStats > 65535) {
                // issue:10869, the portStats is invalid, so this client socket can't open
                System.err.println("The statistics socket port " + portStats + " is invalid.");
                execStat = false;
            }
        } else {
            execStat = false;
        }
        boolean inOSGi = routines.system.BundleUtils.inOSGi();

        if (inOSGi) {
            java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

            if (jobProperties != null && jobProperties.get("context") != null) {
                contextStr = (String) jobProperties.get("context");
            }
        }

        try {
            // call job/subjob with an existing context, like: --context=production. if
            // without this parameter, there will use the default context instead.
            java.io.InputStream inContext =
                Test_delete.class.getClassLoader().getResourceAsStream("irsen/test_delete_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = Test_delete.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
            }
            if (inContext != null) {
                try {
                    // defaultProps is in order to keep the original context value
                    if (context != null && context.isEmpty()) {
                        defaultProps.load(inContext);
                        context = new ContextProperties(defaultProps);
                    }
                } finally {
                    inContext.close();
                }
            } else if (!isDefaultContext) {
                // print info and job continue to run, for case: context_param is not empty.
                System.err.println("Could not find the context " + contextStr);
            }

            if (!context_param.isEmpty()) {
                context.putAll(context_param);
                // set types for params from parentJobs
                for (Object key : context_param.keySet()) {
                    String context_key = key.toString();
                    String context_type = context_param.getContextType(context_key);
                    context.setContextType(context_key, context_type);
                }
            }
            class ContextProcessing {

                private void processContext_0() {}

                public void processAllContext() {
                    processContext_0();
                }
            }

            new ContextProcessing().processAllContext();
        } catch (java.io.IOException ie) {
            System.err.println("Could not load context " + contextStr);
            ie.printStackTrace();
        }

        // get context value from parent directly
        if (parentContextMap != null && !parentContextMap.isEmpty()) {}

        // Resume: init the resumeUtil
        resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
        resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
        resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

        List<String> parametersToEncrypt = new java.util.ArrayList<String>();
        // Resume: jobStart
        resumeUtil.addLog(
            "JOB_STARTED",
            "JOB:" + jobName,
            parent_part_launcher,
            Thread.currentThread().getId() + "",
            "",
            "",
            "",
            "",
            resumeUtil.convertToJsonText(context, parametersToEncrypt)
        );

        if (execStat) {
            try {
                runStat.openSocket(!isChildJob);
                runStat.setAllPID(rootPid, fatherPid, pid, jobName);
                runStat.startThreadStat(clientHost, portStats);
                runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
            } catch (java.io.IOException ioException) {
                ioException.printStackTrace();
            }
        }

        java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
        globalMap.put("concurrentHashMap", concurrentHashMap);

        long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endUsedMemory = 0;
        long end = 0;

        startTime = System.currentTimeMillis();

        this.globalResumeTicket = true; // to run tPreJob

        this.globalResumeTicket = false; // to run others jobs

        try {
            errorCode = null;
            tDBRow_1Process(globalMap);
            if (!"failure".equals(status)) {
                status = "end";
            }
        } catch (TalendException e_tDBRow_1) {
            globalMap.put("tDBRow_1_SUBPROCESS_STATE", -1);

            e_tDBRow_1.printStackTrace();
        }

        this.globalResumeTicket = true; // to run tPostJob

        end = System.currentTimeMillis();

        if (watch) {
            System.out.println((end - startTime) + " milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : Test_delete");
        }

        if (execStat) {
            runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
            runStat.stopThreadStat();
        }
        int returnCode = 0;

        if (errorCode == null) {
            returnCode = status != null && status.equals("failure") ? 1 : 0;
        } else {
            returnCode = errorCode.intValue();
        }
        resumeUtil.addLog(
            "JOB_ENDED",
            "JOB:" + jobName,
            parent_part_launcher,
            Thread.currentThread().getId() + "",
            "",
            "" + returnCode,
            "",
            "",
            ""
        );

        return returnCode;
    }

    // only for OSGi env
    public void destroy() {}

    private java.util.Map<String, Object> getSharedConnections4REST() {
        java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

        return connections;
    }

    private void evalParam(String arg) {
        if (arg.startsWith("--resuming_logs_dir_path")) {
            resuming_logs_dir_path = arg.substring(25);
        } else if (arg.startsWith("--resuming_checkpoint_path")) {
            resuming_checkpoint_path = arg.substring(27);
        } else if (arg.startsWith("--parent_part_launcher")) {
            parent_part_launcher = arg.substring(23);
        } else if (arg.startsWith("--watch")) {
            watch = true;
        } else if (arg.startsWith("--stat_port=")) {
            String portStatsStr = arg.substring(12);
            if (portStatsStr != null && !portStatsStr.equals("null")) {
                portStats = Integer.parseInt(portStatsStr);
            }
        } else if (arg.startsWith("--trace_port=")) {
            portTraces = Integer.parseInt(arg.substring(13));
        } else if (arg.startsWith("--client_host=")) {
            clientHost = arg.substring(14);
        } else if (arg.startsWith("--context=")) {
            contextStr = arg.substring(10);
            isDefaultContext = false;
        } else if (arg.startsWith("--father_pid=")) {
            fatherPid = arg.substring(13);
        } else if (arg.startsWith("--root_pid=")) {
            rootPid = arg.substring(11);
        } else if (arg.startsWith("--father_node=")) {
            fatherNode = arg.substring(14);
        } else if (arg.startsWith("--pid=")) {
            pid = arg.substring(6);
        } else if (arg.startsWith("--context_type")) {
            String keyValue = arg.substring(15);
            int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid == null) {
                    context_param.setContextType(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
                }
            }
        } else if (arg.startsWith("--context_param")) {
            String keyValue = arg.substring(16);
            int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid == null) {
                    context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
                }
            }
        } else if (arg.startsWith("--log4jLevel=")) {
            log4jLevel = arg.substring(13);
        } else if (arg.startsWith("--audit.enabled") && arg.contains("=")) { // for trunjob call
            final int equal = arg.indexOf('=');
            final String key = arg.substring("--".length(), equal);
            System.setProperty(key, arg.substring(equal + 1));
        }
    }

    private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

    private final String[][] escapeChars = {
        { "\\\\", "\\" },
        { "\\n", "\n" },
        { "\\'", "\'" },
        { "\\r", "\r" },
        { "\\f", "\f" },
        { "\\b", "\b" },
        { "\\t", "\t" },
    };

    private String replaceEscapeChars(String keyValue) {
        if (keyValue == null || ("").equals(keyValue.trim())) {
            return keyValue;
        }

        StringBuilder result = new StringBuilder();
        int currIndex = 0;
        while (currIndex < keyValue.length()) {
            int index = -1;
            // judege if the left string includes escape chars
            for (String[] strArray : escapeChars) {
                index = keyValue.indexOf(strArray[0], currIndex);
                if (index >= 0) {
                    result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0], strArray[1]));
                    currIndex = index + strArray[0].length();
                    break;
                }
            }
            // if the left string doesn't include escape chars, append the left into the
            // result
            if (index < 0) {
                result.append(keyValue.substring(currIndex));
                currIndex = currIndex + keyValue.length();
            }
        }

        return result.toString();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getStatus() {
        return status;
    }

    ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 128919 characters generated by Talend Open Studio for Data Integration on the
 * 17 janvier 2022 à 12:25:05 CET
 ************************************************************************************************/
