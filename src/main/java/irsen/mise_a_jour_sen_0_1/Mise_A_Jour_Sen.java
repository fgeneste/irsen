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

package irsen.mise_a_jour_sen_0_1;

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
 * Job: Mise_A_Jour_Sen Purpose: <br>
 * Description:  <br>
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class Mise_A_Jour_Sen implements TalendJob {

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

    //contains type for every context property
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

        //if the stored or passed value is "<TALEND_NULL>" string, it mean null
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
    private final String jobName = "Mise_A_Jour_Sen";
    private final String projectName = "IRSEN";
    public Integer errorCode = null;
    private String currentComponent = "";

    private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
    private static final java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

    private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
    private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
    private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
    public final List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

    private RunStat runStat = new RunStat();

    // OSGi DataSource
    private static final String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

    private static final String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

    public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
        java.util.Map<String, TalendDataSource> talendDataSources = new java.util.HashMap<String, TalendDataSource>();
        for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
            talendDataSources.put(dataSourceEntry.getKey(), new TalendDataSource(dataSourceEntry.getValue()));
        }
        globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
        globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
    }

    public void setDataSourceReferences(List serviceReferences) throws Exception {
        java.util.Map<String, TalendDataSource> talendDataSources = new java.util.HashMap<String, TalendDataSource>();
        java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

        for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
            .getServices(serviceReferences, javax.sql.DataSource.class)
            .entrySet()) {
            dataSources.put(entry.getKey(), entry.getValue());
            talendDataSources.put(entry.getKey(), new TalendDataSource(entry.getValue()));
        }

        globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
        globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
    }

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
                    Mise_A_Jour_Sen.this.exception = e;
                }
            }
            if (!(e instanceof TalendException)) {
                try {
                    for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
                        if (m.getName().compareTo(currentComponent + "_error") == 0) {
                            m.invoke(Mise_A_Jour_Sen.this, new Object[] { e, currentComponent, globalMap });
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

    public void tDBInput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tLogRow_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBSCD_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tRunJob_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tRunJob_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBRow_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBRow_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBRow_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBRow_2_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBInput_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBInput_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tAdvancedHash_row2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tAdvancedHash_row3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBInput_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
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

    public void tRunJob_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
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

    public void tDBRow_2_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
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

    public static class LOGSStruct implements IPersistableRow<LOGSStruct> {

        static final byte[] commonByteArrayLock_IRSEN_Mise_A_Jour_Sen = new byte[0];
        static byte[] commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[0];

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

        public Date SENDATNAI;

        public Date getSENDATNAI() {
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

        public String SENNOMUSECAP;

        public String getSENNOMUSECAP() {
            return this.SENNOMUSECAP;
        }

        public Long SENDPTNUMNAI;

        public Long getSENDPTNUMNAI() {
            return this.SENDPTNUMNAI;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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

        private Date readDate(ObjectInputStream dis) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = dis.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(dis.readLong());
            }
            return dateReturn;
        }

        private Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = unmarshaller.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(unmarshaller.readLong());
            }
            return dateReturn;
        }

        private void writeDate(Date date1, ObjectOutputStream dos) throws IOException {
            if (date1 == null) {
                dos.writeByte(-1);
            } else {
                dos.writeByte(0);
                dos.writeLong(date1.getTime());
            }
        }

        private void writeDate(Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (date1 == null) {
                marshaller.writeByte(-1);
            } else {
                marshaller.writeByte(0);
                marshaller.writeLong(date1.getTime());
            }
        }

        public void readData(ObjectInputStream dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
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

                    this.SENDATNAI = readDate(dis);

                    this.SENLIENAI = readString(dis);

                    this.SENDESPRO = readString(dis);

                    this.PCSCOD42 = readString(dis);

                    this.CATPROCOD = readString(dis);

                    this.SENEMA = readString(dis);

                    this.SENNUMTELSEN = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.SENDPTNUMNAI = null;
                    } else {
                        this.SENDPTNUMNAI = dis.readLong();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
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

                    this.SENDATNAI = readDate(dis);

                    this.SENLIENAI = readString(dis);

                    this.SENDESPRO = readString(dis);

                    this.PCSCOD42 = readString(dis);

                    this.CATPROCOD = readString(dis);

                    this.SENEMA = readString(dis);

                    this.SENNUMTELSEN = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.SENDPTNUMNAI = null;
                    } else {
                        this.SENDPTNUMNAI = dis.readLong();
                    }
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

                // java.util.Date

                writeDate(this.SENDATNAI, dos);

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

                writeString(this.SENNOMUSECAP, dos);

                // Long

                if (this.SENDPTNUMNAI == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.SENDPTNUMNAI);
                }
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

                // java.util.Date

                writeDate(this.SENDATNAI, dos);

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

                writeString(this.SENNOMUSECAP, dos);

                // Long

                if (this.SENDPTNUMNAI == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.SENDPTNUMNAI);
                }
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
            sb.append(",SENDATNAI=" + String.valueOf(SENDATNAI));
            sb.append(",SENLIENAI=" + SENLIENAI);
            sb.append(",SENDESPRO=" + SENDESPRO);
            sb.append(",PCSCOD42=" + PCSCOD42);
            sb.append(",CATPROCOD=" + CATPROCOD);
            sb.append(",SENEMA=" + SENEMA);
            sb.append(",SENNUMTELSEN=" + SENNUMTELSEN);
            sb.append(",SENNOMUSECAP=" + SENNOMUSECAP);
            sb.append(",SENDPTNUMNAI=" + String.valueOf(SENDPTNUMNAI));
            sb.append("]");

            return sb.toString();
        }

        /**
         * Compare keys
         */
        public int compareTo(LOGSStruct other) {
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

    public static class SEN_MAJStruct implements IPersistableRow<SEN_MAJStruct> {

        static final byte[] commonByteArrayLock_IRSEN_Mise_A_Jour_Sen = new byte[0];
        static byte[] commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[0];

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

        public Date sendatnai;

        public Date getSendatnai() {
            return this.sendatnai;
        }

        public String senlienai;

        public String getSenlienai() {
            return this.senlienai;
        }

        public String sendespro;

        public String getSendespro() {
            return this.sendespro;
        }

        public String pcscod42;

        public String getPcscod42() {
            return this.pcscod42;
        }

        public String catprocod;

        public String getCatprocod() {
            return this.catprocod;
        }

        public String senema;

        public String getSenema() {
            return this.senema;
        }

        public String sennumtelsen;

        public String getSennumtelsen() {
            return this.sennumtelsen;
        }

        public String sennomusecap;

        public String getSennomusecap() {
            return this.sennomusecap;
        }

        public Long sendptnumnai;

        public Long getSendptnumnai() {
            return this.sendptnumnai;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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

        private Date readDate(ObjectInputStream dis) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = dis.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(dis.readLong());
            }
            return dateReturn;
        }

        private Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = unmarshaller.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(unmarshaller.readLong());
            }
            return dateReturn;
        }

        private void writeDate(Date date1, ObjectOutputStream dos) throws IOException {
            if (date1 == null) {
                dos.writeByte(-1);
            } else {
                dos.writeByte(0);
                dos.writeLong(date1.getTime());
            }
        }

        private void writeDate(Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (date1 == null) {
                marshaller.writeByte(-1);
            } else {
                marshaller.writeByte(0);
                marshaller.writeLong(date1.getTime());
            }
        }

        public void readData(ObjectInputStream dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
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

                    this.sendespro = readString(dis);

                    this.pcscod42 = readString(dis);

                    this.catprocod = readString(dis);

                    this.senema = readString(dis);

                    this.sennumtelsen = readString(dis);

                    this.sennomusecap = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sendptnumnai = null;
                    } else {
                        this.sendptnumnai = dis.readLong();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
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

                    this.sendespro = readString(dis);

                    this.pcscod42 = readString(dis);

                    this.catprocod = readString(dis);

                    this.senema = readString(dis);

                    this.sennumtelsen = readString(dis);

                    this.sennomusecap = readString(dis);

                    length = dis.readByte();
                    if (length == -1) {
                        this.sendptnumnai = null;
                    } else {
                        this.sendptnumnai = dis.readLong();
                    }
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

                // String

                writeString(this.sendespro, dos);

                // String

                writeString(this.pcscod42, dos);

                // String

                writeString(this.catprocod, dos);

                // String

                writeString(this.senema, dos);

                // String

                writeString(this.sennumtelsen, dos);

                // String

                writeString(this.sennomusecap, dos);

                // Long

                if (this.sendptnumnai == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sendptnumnai);
                }
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

                // String

                writeString(this.sendespro, dos);

                // String

                writeString(this.pcscod42, dos);

                // String

                writeString(this.catprocod, dos);

                // String

                writeString(this.senema, dos);

                // String

                writeString(this.sennumtelsen, dos);

                // String

                writeString(this.sennomusecap, dos);

                // Long

                if (this.sendptnumnai == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.sendptnumnai);
                }
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
            sb.append(",sendespro=" + sendespro);
            sb.append(",pcscod42=" + pcscod42);
            sb.append(",catprocod=" + catprocod);
            sb.append(",senema=" + senema);
            sb.append(",sennumtelsen=" + sennumtelsen);
            sb.append(",sennomusecap=" + sennomusecap);
            sb.append(",sendptnumnai=" + String.valueOf(sendptnumnai));
            sb.append("]");

            return sb.toString();
        }

        /**
         * Compare keys
         */
        public int compareTo(SEN_MAJStruct other) {
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

    public static class row1Struct implements IPersistableRow<row1Struct> {

        static final byte[] commonByteArrayLock_IRSEN_Mise_A_Jour_Sen = new byte[0];
        static byte[] commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[0];

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

        public Date SENDATNAI;

        public Date getSENDATNAI() {
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

        public String PCSCOD42_TO_TRANSLATE;

        public String getPCSCOD42_TO_TRANSLATE() {
            return this.PCSCOD42_TO_TRANSLATE;
        }

        public String CATPROCOD_TO_TRANSLATE;

        public String getCATPROCOD_TO_TRANSLATE() {
            return this.CATPROCOD_TO_TRANSLATE;
        }

        public String SENEMA;

        public String getSENEMA() {
            return this.SENEMA;
        }

        public String SENNUMTELSEN;

        public String getSENNUMTELSEN() {
            return this.SENNUMTELSEN;
        }

        public String SENNOMUSECAP;

        public String getSENNOMUSECAP() {
            return this.SENNOMUSECAP;
        }

        public String SENDPTNUMNAI_TO_TRANSLATE;

        public String getSENDPTNUMNAI_TO_TRANSLATE() {
            return this.SENDPTNUMNAI_TO_TRANSLATE;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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

        private Date readDate(ObjectInputStream dis) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = dis.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(dis.readLong());
            }
            return dateReturn;
        }

        private Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = unmarshaller.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(unmarshaller.readLong());
            }
            return dateReturn;
        }

        private void writeDate(Date date1, ObjectOutputStream dos) throws IOException {
            if (date1 == null) {
                dos.writeByte(-1);
            } else {
                dos.writeByte(0);
                dos.writeLong(date1.getTime());
            }
        }

        private void writeDate(Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (date1 == null) {
                marshaller.writeByte(-1);
            } else {
                marshaller.writeByte(0);
                marshaller.writeLong(date1.getTime());
            }
        }

        public void readData(ObjectInputStream dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
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

                    this.SENDATNAI = readDate(dis);

                    this.SENLIENAI = readString(dis);

                    this.SENDESPRO = readString(dis);

                    this.PCSCOD42_TO_TRANSLATE = readString(dis);

                    this.CATPROCOD_TO_TRANSLATE = readString(dis);

                    this.SENEMA = readString(dis);

                    this.SENNUMTELSEN = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    this.SENDPTNUMNAI_TO_TRANSLATE = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
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

                    this.SENDATNAI = readDate(dis);

                    this.SENLIENAI = readString(dis);

                    this.SENDESPRO = readString(dis);

                    this.PCSCOD42_TO_TRANSLATE = readString(dis);

                    this.CATPROCOD_TO_TRANSLATE = readString(dis);

                    this.SENEMA = readString(dis);

                    this.SENNUMTELSEN = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    this.SENDPTNUMNAI_TO_TRANSLATE = readString(dis);
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

                // java.util.Date

                writeDate(this.SENDATNAI, dos);

                // String

                writeString(this.SENLIENAI, dos);

                // String

                writeString(this.SENDESPRO, dos);

                // String

                writeString(this.PCSCOD42_TO_TRANSLATE, dos);

                // String

                writeString(this.CATPROCOD_TO_TRANSLATE, dos);

                // String

                writeString(this.SENEMA, dos);

                // String

                writeString(this.SENNUMTELSEN, dos);

                // String

                writeString(this.SENNOMUSECAP, dos);

                // String

                writeString(this.SENDPTNUMNAI_TO_TRANSLATE, dos);
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

                // java.util.Date

                writeDate(this.SENDATNAI, dos);

                // String

                writeString(this.SENLIENAI, dos);

                // String

                writeString(this.SENDESPRO, dos);

                // String

                writeString(this.PCSCOD42_TO_TRANSLATE, dos);

                // String

                writeString(this.CATPROCOD_TO_TRANSLATE, dos);

                // String

                writeString(this.SENEMA, dos);

                // String

                writeString(this.SENNUMTELSEN, dos);

                // String

                writeString(this.SENNOMUSECAP, dos);

                // String

                writeString(this.SENDPTNUMNAI_TO_TRANSLATE, dos);
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
            sb.append(",SENDATNAI=" + String.valueOf(SENDATNAI));
            sb.append(",SENLIENAI=" + SENLIENAI);
            sb.append(",SENDESPRO=" + SENDESPRO);
            sb.append(",PCSCOD42_TO_TRANSLATE=" + PCSCOD42_TO_TRANSLATE);
            sb.append(",CATPROCOD_TO_TRANSLATE=" + CATPROCOD_TO_TRANSLATE);
            sb.append(",SENEMA=" + SENEMA);
            sb.append(",SENNUMTELSEN=" + SENNUMTELSEN);
            sb.append(",SENNOMUSECAP=" + SENNOMUSECAP);
            sb.append(",SENDPTNUMNAI_TO_TRANSLATE=" + SENDPTNUMNAI_TO_TRANSLATE);
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

    public static class after_tDBInput_1Struct implements IPersistableRow<after_tDBInput_1Struct> {

        static final byte[] commonByteArrayLock_IRSEN_Mise_A_Jour_Sen = new byte[0];
        static byte[] commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[0];

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

        public Date SENDATNAI;

        public Date getSENDATNAI() {
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

        public String PCSCOD42_TO_TRANSLATE;

        public String getPCSCOD42_TO_TRANSLATE() {
            return this.PCSCOD42_TO_TRANSLATE;
        }

        public String CATPROCOD_TO_TRANSLATE;

        public String getCATPROCOD_TO_TRANSLATE() {
            return this.CATPROCOD_TO_TRANSLATE;
        }

        public String SENEMA;

        public String getSENEMA() {
            return this.SENEMA;
        }

        public String SENNUMTELSEN;

        public String getSENNUMTELSEN() {
            return this.SENNUMTELSEN;
        }

        public String SENNOMUSECAP;

        public String getSENNOMUSECAP() {
            return this.SENNOMUSECAP;
        }

        public String SENDPTNUMNAI_TO_TRANSLATE;

        public String getSENDPTNUMNAI_TO_TRANSLATE() {
            return this.SENDPTNUMNAI_TO_TRANSLATE;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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

        private Date readDate(ObjectInputStream dis) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = dis.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(dis.readLong());
            }
            return dateReturn;
        }

        private Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = unmarshaller.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(unmarshaller.readLong());
            }
            return dateReturn;
        }

        private void writeDate(Date date1, ObjectOutputStream dos) throws IOException {
            if (date1 == null) {
                dos.writeByte(-1);
            } else {
                dos.writeByte(0);
                dos.writeLong(date1.getTime());
            }
        }

        private void writeDate(Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (date1 == null) {
                marshaller.writeByte(-1);
            } else {
                marshaller.writeByte(0);
                marshaller.writeLong(date1.getTime());
            }
        }

        public void readData(ObjectInputStream dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
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

                    this.SENDATNAI = readDate(dis);

                    this.SENLIENAI = readString(dis);

                    this.SENDESPRO = readString(dis);

                    this.PCSCOD42_TO_TRANSLATE = readString(dis);

                    this.CATPROCOD_TO_TRANSLATE = readString(dis);

                    this.SENEMA = readString(dis);

                    this.SENNUMTELSEN = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    this.SENDPTNUMNAI_TO_TRANSLATE = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
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

                    this.SENDATNAI = readDate(dis);

                    this.SENLIENAI = readString(dis);

                    this.SENDESPRO = readString(dis);

                    this.PCSCOD42_TO_TRANSLATE = readString(dis);

                    this.CATPROCOD_TO_TRANSLATE = readString(dis);

                    this.SENEMA = readString(dis);

                    this.SENNUMTELSEN = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    this.SENDPTNUMNAI_TO_TRANSLATE = readString(dis);
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

                // java.util.Date

                writeDate(this.SENDATNAI, dos);

                // String

                writeString(this.SENLIENAI, dos);

                // String

                writeString(this.SENDESPRO, dos);

                // String

                writeString(this.PCSCOD42_TO_TRANSLATE, dos);

                // String

                writeString(this.CATPROCOD_TO_TRANSLATE, dos);

                // String

                writeString(this.SENEMA, dos);

                // String

                writeString(this.SENNUMTELSEN, dos);

                // String

                writeString(this.SENNOMUSECAP, dos);

                // String

                writeString(this.SENDPTNUMNAI_TO_TRANSLATE, dos);
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

                // java.util.Date

                writeDate(this.SENDATNAI, dos);

                // String

                writeString(this.SENLIENAI, dos);

                // String

                writeString(this.SENDESPRO, dos);

                // String

                writeString(this.PCSCOD42_TO_TRANSLATE, dos);

                // String

                writeString(this.CATPROCOD_TO_TRANSLATE, dos);

                // String

                writeString(this.SENEMA, dos);

                // String

                writeString(this.SENNUMTELSEN, dos);

                // String

                writeString(this.SENNOMUSECAP, dos);

                // String

                writeString(this.SENDPTNUMNAI_TO_TRANSLATE, dos);
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
            sb.append(",SENDATNAI=" + String.valueOf(SENDATNAI));
            sb.append(",SENLIENAI=" + SENLIENAI);
            sb.append(",SENDESPRO=" + SENDESPRO);
            sb.append(",PCSCOD42_TO_TRANSLATE=" + PCSCOD42_TO_TRANSLATE);
            sb.append(",CATPROCOD_TO_TRANSLATE=" + CATPROCOD_TO_TRANSLATE);
            sb.append(",SENEMA=" + SENEMA);
            sb.append(",SENNUMTELSEN=" + SENNUMTELSEN);
            sb.append(",SENNOMUSECAP=" + SENNOMUSECAP);
            sb.append(",SENDPTNUMNAI_TO_TRANSLATE=" + SENDPTNUMNAI_TO_TRANSLATE);
            sb.append("]");

            return sb.toString();
        }

        /**
         * Compare keys
         */
        public int compareTo(after_tDBInput_1Struct other) {
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

    public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
        globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

        final boolean execStat = this.execStat;

        String iterateId = "";

        String currentComponent = "";
        java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

        try {
            // TDI-39566 avoid throwing an useless Exception
            boolean resumeIt = true;
            if (globalResumeTicket == false && resumeEntryMethodName != null) {
                String currentMethodName = new Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { //start the resume
                globalResumeTicket = true;

                tDBInput_2Process(globalMap);
                tDBInput_3Process(globalMap);

                row1Struct row1 = new row1Struct();
                LOGSStruct LOGS = new LOGSStruct();
                SEN_MAJStruct SEN_MAJ = new SEN_MAJStruct();

                /**
                 * [tLogRow_1 begin ] start
                 */

                ok_Hash.put("tLogRow_1", false);
                start_Hash.put("tLogRow_1", System.currentTimeMillis());

                currentComponent = "tLogRow_1";

                if (execStat) {
                    runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "LOGS");
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
                 * [tDBSCD_1 begin ] start
                 */

                ok_Hash.put("tDBSCD_1", false);
                start_Hash.put("tDBSCD_1", System.currentTimeMillis());

                currentComponent = "tDBSCD_1";

                if (execStat) {
                    runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "SEN_MAJ");
                }

                int tos_count_tDBSCD_1 = 0;

                class SCDSK_tDBSCD_1 {

                    private int hashCode;
                    public boolean hashCodeDirty = true;

                    public boolean equals(Object obj) {
                        if (this == obj) return true;
                        if (obj == null) return false;
                        if (getClass() != obj.getClass()) return false;
                        final SCDSK_tDBSCD_1 other = (SCDSK_tDBSCD_1) obj;
                        return true;
                    }

                    public int hashCode() {
                        if (hashCodeDirty) {
                            int prime = 31;
                            hashCodeDirty = false;
                        }
                        return hashCode;
                    }
                }

                class SCDStruct_tDBSCD_1 {}

                int nb_line_update_tDBSCD_1 = 0;
                int nb_line_inserted_tDBSCD_1 = 0;
                int nb_line_rejected_tDBSCD_1 = 0;
                String dbSchema_tDBSCD_1 = "senateurs";
                Class.forName("org.postgresql.Driver");
                String connectionString_tDBSCD_1 = "jdbc:postgresql://" + "brandy-test.senat.fr" + ":" + "5603" + "/" + "sendevrep2";

                final String decryptedPassword_tDBSCD_1 = PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:BNaRQ0jG1++er3+t+UB7nyAyY+R7ezYKKMeSV+xc8OX6v4fL7Ecf/A=="
                );

                java.sql.Connection connection_tDBSCD_1 = java.sql.DriverManager.getConnection(
                    connectionString_tDBSCD_1,
                    "pgdba",
                    decryptedPassword_tDBSCD_1
                );
                String tableName_tDBSCD_1 = null;
                if (dbSchema_tDBSCD_1 == null || dbSchema_tDBSCD_1.trim().length() == 0) {
                    tableName_tDBSCD_1 = "sen";
                } else {
                    tableName_tDBSCD_1 = dbSchema_tDBSCD_1 + "\".\"" + "sen";
                }
                java.sql.Timestamp timestamp_tDBSCD_1 = null;
                String tmpValue_tDBSCD_1 = null;
                String search_tDBSCD_1 = "SELECT  FROM \"" + tableName_tDBSCD_1 + "\"";
                java.sql.Statement statement_tDBSCD_1 = connection_tDBSCD_1.createStatement();
                java.sql.ResultSet resultSet_tDBSCD_1 = statement_tDBSCD_1.executeQuery(search_tDBSCD_1);
                java.util.Map<SCDSK_tDBSCD_1, SCDStruct_tDBSCD_1> cache_tDBSCD_1 = new java.util.HashMap<SCDSK_tDBSCD_1, SCDStruct_tDBSCD_1>();
                while (resultSet_tDBSCD_1.next()) {
                    SCDSK_tDBSCD_1 sk_tDBSCD_1 = new SCDSK_tDBSCD_1();
                    SCDStruct_tDBSCD_1 row_tDBSCD_1 = new SCDStruct_tDBSCD_1();
                    cache_tDBSCD_1.put(sk_tDBSCD_1, row_tDBSCD_1);
                }
                resultSet_tDBSCD_1.close();
                statement_tDBSCD_1.close();
                String insertionSQL_tDBSCD_1 = "INSERT INTO \"" + tableName_tDBSCD_1 + "\"() VALUES()";
                java.sql.PreparedStatement insertionStatement_tDBSCD_1 = connection_tDBSCD_1.prepareStatement(insertionSQL_tDBSCD_1);

                SCDSK_tDBSCD_1 lookUpKey_tDBSCD_1 = null;
                SCDStruct_tDBSCD_1 lookUpValue_tDBSCD_1 = null;

                /**
                 * [tDBSCD_1 begin ] stop
                 */

                /**
                 * [tMap_1 begin ] start
                 */

                ok_Hash.put("tMap_1", false);
                start_Hash.put("tMap_1", System.currentTimeMillis());

                currentComponent = "tMap_1";

                if (execStat) {
                    runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
                }

                int tos_count_tMap_1 = 0;

                // ###############################
                // # Lookup's keys initialization

                org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) (
                    (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) globalMap.get("tHash_Lookup_row2")
                );

                row2Struct row2HashKey = new row2Struct();
                row2Struct row2Default = new row2Struct();

                org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) (
                    (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) globalMap.get("tHash_Lookup_row3")
                );

                row3Struct row3HashKey = new row3Struct();
                row3Struct row3Default = new row3Struct();
                // ###############################

                // ###############################
                // # Vars initialization
                class Var__tMap_1__Struct {}
                Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
                // ###############################

                // ###############################
                // # Outputs initialization
                LOGSStruct LOGS_tmp = new LOGSStruct();
                SEN_MAJStruct SEN_MAJ_tmp = new SEN_MAJStruct();
                // ###############################

                /**
                 * [tMap_1 begin ] stop
                 */

                /**
                 * [tDBInput_1 begin ] start
                 */

                ok_Hash.put("tDBInput_1", false);
                start_Hash.put("tDBInput_1", System.currentTimeMillis());

                currentComponent = "tDBInput_1";

                int tos_count_tDBInput_1 = 0;

                int nb_line_tDBInput_1 = 0;
                java.sql.Connection conn_tDBInput_1 = null;
                String driverClass_tDBInput_1 = "org.postgresql.Driver";
                Class jdbcclazz_tDBInput_1 = Class.forName(driverClass_tDBInput_1);
                String dbUser_tDBInput_1 = "postgres";

                final String decryptedPassword_tDBInput_1 = PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:Aup5ur6M8UjxgTU5kU71Q0DB+bpqWy0u5MbSTg=="
                );

                String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

                String url_tDBInput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "irsen";

                conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1, dbPwd_tDBInput_1);

                conn_tDBInput_1.setAutoCommit(false);

                java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

                String dbquery_tDBInput_1 = "Select \n	* from sen";

                globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
                java.sql.ResultSet rs_tDBInput_1 = null;

                try {
                    rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
                    java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
                    int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

                    String tmpContent_tDBInput_1 = null;

                    while (rs_tDBInput_1.next()) {
                        nb_line_tDBInput_1++;

                        if (colQtyInRs_tDBInput_1 < 1) {
                            row1.SENMAT = null;
                        } else {
                            row1.SENMAT = JDBCUtil.getString(rs_tDBInput_1, 1, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 2) {
                            row1.QUACOD = null;
                        } else {
                            row1.QUACOD = JDBCUtil.getString(rs_tDBInput_1, 2, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 3) {
                            row1.SENNOMUSE = null;
                        } else {
                            row1.SENNOMUSE = JDBCUtil.getString(rs_tDBInput_1, 3, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 4) {
                            row1.SENNOMPAT = null;
                        } else {
                            row1.SENNOMPAT = JDBCUtil.getString(rs_tDBInput_1, 4, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 5) {
                            row1.SENNOMMAR = null;
                        } else {
                            row1.SENNOMMAR = JDBCUtil.getString(rs_tDBInput_1, 5, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 6) {
                            row1.SENNOMTEC = null;
                        } else {
                            row1.SENNOMTEC = JDBCUtil.getString(rs_tDBInput_1, 6, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 7) {
                            row1.SENPRENOMUSE = null;
                        } else {
                            row1.SENPRENOMUSE = JDBCUtil.getString(rs_tDBInput_1, 7, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 8) {
                            row1.SENPRENOMCIV = null;
                        } else {
                            row1.SENPRENOMCIV = JDBCUtil.getString(rs_tDBInput_1, 8, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 9) {
                            row1.SENDATNAI = null;
                        } else {
                            row1.SENDATNAI = JDBCUtil.getDate(rs_tDBInput_1, 9);
                        }
                        if (colQtyInRs_tDBInput_1 < 10) {
                            row1.SENLIENAI = null;
                        } else {
                            row1.SENLIENAI = JDBCUtil.getString(rs_tDBInput_1, 10, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 11) {
                            row1.SENDESPRO = null;
                        } else {
                            row1.SENDESPRO = JDBCUtil.getString(rs_tDBInput_1, 11, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 12) {
                            row1.PCSCOD42_TO_TRANSLATE = null;
                        } else {
                            row1.PCSCOD42_TO_TRANSLATE = JDBCUtil.getString(rs_tDBInput_1, 12, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 13) {
                            row1.CATPROCOD_TO_TRANSLATE = null;
                        } else {
                            row1.CATPROCOD_TO_TRANSLATE = JDBCUtil.getString(rs_tDBInput_1, 13, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 14) {
                            row1.SENEMA = null;
                        } else {
                            row1.SENEMA = JDBCUtil.getString(rs_tDBInput_1, 14, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 15) {
                            row1.SENNUMTELSEN = null;
                        } else {
                            row1.SENNUMTELSEN = JDBCUtil.getString(rs_tDBInput_1, 15, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 16) {
                            row1.SENNOMUSECAP = null;
                        } else {
                            row1.SENNOMUSECAP = JDBCUtil.getString(rs_tDBInput_1, 16, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 17) {
                            row1.SENDPTNUMNAI_TO_TRANSLATE = null;
                        } else {
                            row1.SENDPTNUMNAI_TO_TRANSLATE = JDBCUtil.getString(rs_tDBInput_1, 17, false);
                        }

                        /**
                         * [tDBInput_1 begin ] stop
                         */

                        /**
                         * [tDBInput_1 main ] start
                         */

                        currentComponent = "tDBInput_1";

                        tos_count_tDBInput_1++;

                        /**
                         * [tDBInput_1 main ] stop
                         */

                        /**
                         * [tDBInput_1 process_data_begin ] start
                         */

                        currentComponent = "tDBInput_1";

                        /**
                         * [tDBInput_1 process_data_begin ] stop
                         */

                        /**
                         * [tMap_1 main ] start
                         */

                        currentComponent = "tMap_1";

                        if (execStat) {
                            runStat.updateStatOnConnection(iterateId, 1, 1, "row1");
                        }

                        boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

                        // ###############################
                        // # Input tables (lookups)
                        boolean rejectedInnerJoin_tMap_1 = false;
                        boolean mainRowRejected_tMap_1 = false;

                        ///////////////////////////////////////////////
                        // Starting Lookup Table "row2"
                        ///////////////////////////////////////////////

                        boolean forceLooprow2 = false;

                        row2Struct row2ObjectFromLookup = null;

                        if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020
                            hasCasePrimitiveKeyWithNull_tMap_1 = false;

                            row2HashKey.dptcod = row1.SENDPTNUMNAI_TO_TRANSLATE;

                            row2HashKey.hashCodeDirty = true;

                            tHash_Lookup_row2.lookup(row2HashKey);
                        } // G_TM_M_020

                        if (tHash_Lookup_row2 != null && tHash_Lookup_row2.getCount(row2HashKey) > 1) { // G 071
                            //System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row2' and it contains more one result from keys :  row2.dptcod = '" + row2HashKey.dptcod + "'");
                        } // G 071

                        row2Struct row2 = null;

                        row2Struct fromLookup_row2 = null;
                        row2 = row2Default;

                        if (tHash_Lookup_row2 != null && tHash_Lookup_row2.hasNext()) { // G 099
                            fromLookup_row2 = tHash_Lookup_row2.next();
                        } // G 099

                        if (fromLookup_row2 != null) {
                            row2 = fromLookup_row2;
                        }

                        ///////////////////////////////////////////////
                        // Starting Lookup Table "row3"
                        ///////////////////////////////////////////////

                        boolean forceLooprow3 = false;

                        row3Struct row3ObjectFromLookup = null;

                        if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020
                            hasCasePrimitiveKeyWithNull_tMap_1 = false;

                            row3HashKey.pcs42cod = row1.PCSCOD42_TO_TRANSLATE;

                            row3HashKey.hashCodeDirty = true;

                            tHash_Lookup_row3.lookup(row3HashKey);
                        } // G_TM_M_020

                        if (tHash_Lookup_row3 != null && tHash_Lookup_row3.getCount(row3HashKey) > 1) { // G 071
                            //System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row3' and it contains more one result from keys :  row3.pcs42cod = '" + row3HashKey.pcs42cod + "'");
                        } // G 071

                        row3Struct row3 = null;

                        row3Struct fromLookup_row3 = null;
                        row3 = row3Default;

                        if (tHash_Lookup_row3 != null && tHash_Lookup_row3.hasNext()) { // G 099
                            fromLookup_row3 = tHash_Lookup_row3.next();
                        } // G 099

                        if (fromLookup_row3 != null) {
                            row3 = fromLookup_row3;
                        }

                        // ###############################
                        { // start of Var scope
                            // ###############################
                            // # Vars tables

                            Var__tMap_1__Struct Var = Var__tMap_1; // ###############################
                            // ###############################
                            // # Output tables

                            LOGS = null;
                            SEN_MAJ = null;

                            // # Output table : 'LOGS'
                            LOGS_tmp.SENMAT = row1.SENMAT;
                            LOGS_tmp.QUACOD = row1.QUACOD;
                            LOGS_tmp.SENNOMUSE = row1.SENNOMUSE;
                            LOGS_tmp.SENNOMPAT = row1.SENNOMPAT;
                            LOGS_tmp.SENNOMMAR = row1.SENNOMMAR;
                            LOGS_tmp.SENNOMTEC = row1.SENNOMTEC;
                            LOGS_tmp.SENPRENOMUSE = row1.SENPRENOMUSE;
                            LOGS_tmp.SENPRENOMCIV = row1.SENPRENOMCIV;
                            LOGS_tmp.SENDATNAI = row1.SENDATNAI;
                            LOGS_tmp.SENLIENAI = row1.SENLIENAI;
                            LOGS_tmp.SENDESPRO = row1.SENDESPRO;
                            LOGS_tmp.PCSCOD42 = row3.pcscod;
                            LOGS_tmp.CATPROCOD = row1.CATPROCOD_TO_TRANSLATE;
                            LOGS_tmp.SENEMA = row1.SENEMA;
                            LOGS_tmp.SENNUMTELSEN = row1.SENNUMTELSEN;
                            LOGS_tmp.SENNOMUSECAP = row1.SENNOMUSECAP;
                            LOGS_tmp.SENDPTNUMNAI = row2.dptnum;
                            LOGS = LOGS_tmp;

                            // # Output table : 'SEN_MAJ'
                            SEN_MAJ_tmp.senmat = row1.SENMAT;
                            SEN_MAJ_tmp.quacod = row1.QUACOD;
                            SEN_MAJ_tmp.sennomuse = row1.SENNOMUSE;
                            SEN_MAJ_tmp.sennompat = row1.SENNOMPAT;
                            SEN_MAJ_tmp.sennommar = row1.SENNOMMAR;
                            SEN_MAJ_tmp.sennomtec = row1.SENNOMTEC;
                            SEN_MAJ_tmp.senprenomuse = row1.SENPRENOMUSE;
                            SEN_MAJ_tmp.senprenomciv = row1.SENPRENOMCIV;
                            SEN_MAJ_tmp.sendatnai = row1.SENDATNAI;
                            SEN_MAJ_tmp.senlienai = row1.SENLIENAI;
                            SEN_MAJ_tmp.sendespro = row1.SENDESPRO;
                            SEN_MAJ_tmp.pcscod42 = row3.pcscod;
                            SEN_MAJ_tmp.catprocod = row1.CATPROCOD_TO_TRANSLATE;
                            SEN_MAJ_tmp.senema = row1.SENEMA;
                            SEN_MAJ_tmp.sennumtelsen = row1.SENNUMTELSEN;
                            SEN_MAJ_tmp.sennomusecap = row1.SENNOMUSECAP;
                            SEN_MAJ_tmp.sendptnumnai = row2.dptnum;
                            SEN_MAJ = SEN_MAJ_tmp;
                            // ###############################

                        } // end of Var scope

                        rejectedInnerJoin_tMap_1 = false;

                        tos_count_tMap_1++;

                        /**
                         * [tMap_1 main ] stop
                         */

                        /**
                         * [tMap_1 process_data_begin ] start
                         */

                        currentComponent = "tMap_1";

                        /**
                         * [tMap_1 process_data_begin ] stop
                         */
                        // Start of branch "LOGS"
                        if (LOGS != null) {
                            /**
                             * [tLogRow_1 main ] start
                             */

                            currentComponent = "tLogRow_1";

                            if (execStat) {
                                runStat.updateStatOnConnection(iterateId, 1, 1, "LOGS");
                            }

                            ///////////////////////

                            strBuffer_tLogRow_1 = new StringBuilder();

                            if (LOGS.SENMAT != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENMAT));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.QUACOD != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.QUACOD));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENNOMUSE != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENNOMUSE));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENNOMPAT != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENNOMPAT));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENNOMMAR != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENNOMMAR));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENNOMTEC != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENNOMTEC));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENPRENOMUSE != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENPRENOMUSE));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENPRENOMCIV != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENPRENOMCIV));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENDATNAI != null) { //
                                strBuffer_tLogRow_1.append(FormatterUtils.format_Date(LOGS.SENDATNAI, "dd-MM-yyyy"));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENLIENAI != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENLIENAI));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENDESPRO != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENDESPRO));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.PCSCOD42 != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.PCSCOD42));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.CATPROCOD != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.CATPROCOD));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENEMA != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENEMA));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENNUMTELSEN != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENNUMTELSEN));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENNOMUSECAP != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENNOMUSECAP));
                            } //

                            strBuffer_tLogRow_1.append("|");

                            if (LOGS.SENDPTNUMNAI != null) { //
                                strBuffer_tLogRow_1.append(String.valueOf(LOGS.SENDPTNUMNAI));
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

                        } // End of branch "LOGS"

                        // Start of branch "SEN_MAJ"
                        if (SEN_MAJ != null) {
                            /**
                             * [tDBSCD_1 main ] start
                             */

                            currentComponent = "tDBSCD_1";

                            if (execStat) {
                                runStat.updateStatOnConnection(iterateId, 1, 1, "SEN_MAJ");
                            }

                            try {
                                lookUpKey_tDBSCD_1 = new SCDSK_tDBSCD_1();
                                lookUpKey_tDBSCD_1.hashCodeDirty = true;
                                lookUpValue_tDBSCD_1 = cache_tDBSCD_1.get(lookUpKey_tDBSCD_1);
                                if (lookUpValue_tDBSCD_1 == null) {
                                    lookUpValue_tDBSCD_1 = new SCDStruct_tDBSCD_1();

                                    nb_line_inserted_tDBSCD_1 += insertionStatement_tDBSCD_1.executeUpdate();
                                } else {}
                            } catch (Exception e) { //catch
                                globalMap.put("tDBSCD_1_ERROR_MESSAGE", e.getMessage());

                                System.err.print(e.getMessage());
                            } //end catch

                            cache_tDBSCD_1.put(lookUpKey_tDBSCD_1, lookUpValue_tDBSCD_1);

                            tos_count_tDBSCD_1++;

                            /**
                             * [tDBSCD_1 main ] stop
                             */

                            /**
                             * [tDBSCD_1 process_data_begin ] start
                             */

                            currentComponent = "tDBSCD_1";

                            /**
                             * [tDBSCD_1 process_data_begin ] stop
                             */

                            /**
                             * [tDBSCD_1 process_data_end ] start
                             */

                            currentComponent = "tDBSCD_1";
                            /**
                             * [tDBSCD_1 process_data_end ] stop
                             */

                        } // End of branch "SEN_MAJ"

                        /**
                         * [tMap_1 process_data_end ] start
                         */

                        currentComponent = "tMap_1";

                        /**
                         * [tMap_1 process_data_end ] stop
                         */

                        /**
                         * [tDBInput_1 process_data_end ] start
                         */

                        currentComponent = "tDBInput_1";

                        /**
                         * [tDBInput_1 process_data_end ] stop
                         */

                        /**
                         * [tDBInput_1 end ] start
                         */

                        currentComponent = "tDBInput_1";
                    }
                } finally {
                    if (rs_tDBInput_1 != null) {
                        rs_tDBInput_1.close();
                    }
                    if (stmt_tDBInput_1 != null) {
                        stmt_tDBInput_1.close();
                    }
                    if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {
                        conn_tDBInput_1.commit();

                        conn_tDBInput_1.close();

                        if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) && BundleUtils.inOSGi()) {
                            Class
                                .forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
                                .getMethod("checkedShutdown")
                                .invoke(null, (Object[]) null);
                        }
                    }
                }
                globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);

                ok_Hash.put("tDBInput_1", true);
                end_Hash.put("tDBInput_1", System.currentTimeMillis());

                /**
                 * [tDBInput_1 end ] stop
                 */

                /**
                 * [tMap_1 end ] start
                 */

                currentComponent = "tMap_1";

                // ###############################
                // # Lookup hashes releasing
                if (tHash_Lookup_row2 != null) {
                    tHash_Lookup_row2.endGet();
                }
                globalMap.remove("tHash_Lookup_row2");

                if (tHash_Lookup_row3 != null) {
                    tHash_Lookup_row3.endGet();
                }
                globalMap.remove("tHash_Lookup_row3");

                // ###############################

                if (execStat) {
                    runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
                }

                ok_Hash.put("tMap_1", true);
                end_Hash.put("tMap_1", System.currentTimeMillis());

                /**
                 * [tMap_1 end ] stop
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
                    runStat.updateStat(resourceMap, iterateId, 2, 0, "LOGS");
                }

                ok_Hash.put("tLogRow_1", true);
                end_Hash.put("tLogRow_1", System.currentTimeMillis());

                /**
                 * [tLogRow_1 end ] stop
                 */

                /**
                 * [tDBSCD_1 end ] start
                 */

                currentComponent = "tDBSCD_1";

                insertionStatement_tDBSCD_1.close();
                if (connection_tDBSCD_1 != null && !connection_tDBSCD_1.isClosed()) {
                    connection_tDBSCD_1.close();
                }
                globalMap.put("tDBSCD_1_NB_LINE_UPDATED", nb_line_update_tDBSCD_1);
                globalMap.put("tDBSCD_1_NB_LINE_INSERTED", nb_line_inserted_tDBSCD_1);
                globalMap.put("tDBSCD_1_NB_LINE_REJECTED", nb_line_rejected_tDBSCD_1);

                if (execStat) {
                    runStat.updateStat(resourceMap, iterateId, 2, 0, "SEN_MAJ");
                }

                ok_Hash.put("tDBSCD_1", true);
                end_Hash.put("tDBSCD_1", System.currentTimeMillis());

                if (execStat) {
                    runStat.updateStatOnConnection("OnComponentOk3", 0, "ok");
                }
                tRunJob_1Process(globalMap);
                /**
                 * [tDBSCD_1 end ] stop
                 */

            } //end the resume
        } catch (Exception e) {
            TalendException te = new TalendException(e, currentComponent, globalMap);

            throw te;
        } catch (Error error) {
            runStat.stopThreadStat();

            throw error;
        } finally {
            //free memory for "tMap_1"
            globalMap.remove("tHash_Lookup_row2");

            //free memory for "tMap_1"
            globalMap.remove("tHash_Lookup_row3");

            try {
                /**
                 * [tDBInput_1 finally ] start
                 */

                currentComponent = "tDBInput_1";

                /**
                 * [tDBInput_1 finally ] stop
                 */

                /**
                 * [tMap_1 finally ] start
                 */

                currentComponent = "tMap_1";

                /**
                 * [tMap_1 finally ] stop
                 */

                /**
                 * [tLogRow_1 finally ] start
                 */

                currentComponent = "tLogRow_1";

                /**
                 * [tLogRow_1 finally ] stop
                 */

                /**
                 * [tDBSCD_1 finally ] start
                 */

                currentComponent = "tDBSCD_1";
                /**
                 * [tDBSCD_1 finally ] stop
                 */

            } catch (Exception e) {
                //ignore
            } catch (Error error) {
                //ignore
            }
            resourceMap = null;
        }

        globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
    }

    public void tRunJob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
        globalMap.put("tRunJob_1_SUBPROCESS_STATE", 0);

        final boolean execStat = this.execStat;

        String iterateId = "";

        String currentComponent = "";
        java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

        try {
            // TDI-39566 avoid throwing an useless Exception
            boolean resumeIt = true;
            if (globalResumeTicket == false && resumeEntryMethodName != null) {
                String currentMethodName = new Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { //start the resume
                globalResumeTicket = true;

                /**
                 * [tRunJob_1 begin ] start
                 */

                ok_Hash.put("tRunJob_1", false);
                start_Hash.put("tRunJob_1", System.currentTimeMillis());

                currentComponent = "tRunJob_1";

                int tos_count_tRunJob_1 = 0;

                /**
                 * [tRunJob_1 begin ] stop
                 */

                /**
                 * [tRunJob_1 main ] start
                 */

                currentComponent = "tRunJob_1";

                List<String> paraList_tRunJob_1 = new java.util.ArrayList<String>();

                paraList_tRunJob_1.add("--father_pid=" + pid);

                paraList_tRunJob_1.add("--root_pid=" + rootPid);

                paraList_tRunJob_1.add("--father_node=tRunJob_1");

                paraList_tRunJob_1.add("--context=Default");

                if (enableLogStash) {
                    paraList_tRunJob_1.add("--audit.enabled=" + enableLogStash);
                }

                //for feature:10589

                paraList_tRunJob_1.add("--stat_port=" + portStats);

                if (resuming_logs_dir_path != null) {
                    paraList_tRunJob_1.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
                }
                String childResumePath_tRunJob_1 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
                String tRunJobName_tRunJob_1 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
                if ("tRunJob_1".equals(tRunJobName_tRunJob_1) && childResumePath_tRunJob_1 != null) {
                    paraList_tRunJob_1.add("--resuming_checkpoint_path=" + ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
                }
                paraList_tRunJob_1.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_1");

                java.util.Map<String, Object> parentContextMap_tRunJob_1 = new java.util.HashMap<String, Object>();

                Object obj_tRunJob_1 = null;

                irsen.historique_nom_0_1.Historique_Nom childJob_tRunJob_1 = new irsen.historique_nom_0_1.Historique_Nom();
                // pass DataSources
                java.util.Map<String, TalendDataSource> talendDataSources_tRunJob_1 = (java.util.Map<String, TalendDataSource>) globalMap.get(
                    KEY_DB_DATASOURCES
                );
                if (null != talendDataSources_tRunJob_1) {
                    java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_1 = new java.util.HashMap<String, javax.sql.DataSource>();
                    for (java.util.Map.Entry<String, TalendDataSource> talendDataSourceEntry_tRunJob_1 : talendDataSources_tRunJob_1.entrySet()) {
                        dataSources_tRunJob_1.put(
                            talendDataSourceEntry_tRunJob_1.getKey(),
                            talendDataSourceEntry_tRunJob_1.getValue().getRawDataSource()
                        );
                    }
                    childJob_tRunJob_1.setDataSources(dataSources_tRunJob_1);
                }

                childJob_tRunJob_1.parentContextMap = parentContextMap_tRunJob_1;

                String[][] childReturn_tRunJob_1 = childJob_tRunJob_1.runJob(
                    (String[]) paraList_tRunJob_1.toArray(new String[paraList_tRunJob_1.size()])
                );

                if (childJob_tRunJob_1.getErrorCode() == null) {
                    globalMap.put(
                        "tRunJob_1_CHILD_RETURN_CODE",
                        childJob_tRunJob_1.getStatus() != null && ("failure").equals(childJob_tRunJob_1.getStatus()) ? 1 : 0
                    );
                } else {
                    globalMap.put("tRunJob_1_CHILD_RETURN_CODE", childJob_tRunJob_1.getErrorCode());
                }
                if (childJob_tRunJob_1.getExceptionStackTrace() != null) {
                    globalMap.put("tRunJob_1_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_1.getExceptionStackTrace());
                }
                errorCode = childJob_tRunJob_1.getErrorCode();
                if (childJob_tRunJob_1.getErrorCode() != null || ("failure").equals(childJob_tRunJob_1.getStatus())) {
                    Exception ce_tRunJob_1 = childJob_tRunJob_1.getException();
                    throw new RuntimeException(
                        "Child job running failed.\n" +
                        ((ce_tRunJob_1 != null) ? (ce_tRunJob_1.getClass().getName() + ": " + ce_tRunJob_1.getMessage()) : "")
                    );
                }

                tos_count_tRunJob_1++;

                /**
                 * [tRunJob_1 main ] stop
                 */

                /**
                 * [tRunJob_1 process_data_begin ] start
                 */

                currentComponent = "tRunJob_1";

                /**
                 * [tRunJob_1 process_data_begin ] stop
                 */

                /**
                 * [tRunJob_1 process_data_end ] start
                 */

                currentComponent = "tRunJob_1";

                /**
                 * [tRunJob_1 process_data_end ] stop
                 */

                /**
                 * [tRunJob_1 end ] start
                 */

                currentComponent = "tRunJob_1";

                ok_Hash.put("tRunJob_1", true);
                end_Hash.put("tRunJob_1", System.currentTimeMillis());

                if (execStat) {
                    runStat.updateStatOnConnection("OnComponentOk1", 0, "ok");
                }
                tDBRow_1Process(globalMap);
                /**
                 * [tRunJob_1 end ] stop
                 */
            } //end the resume
        } catch (Exception e) {
            TalendException te = new TalendException(e, currentComponent, globalMap);

            throw te;
        } catch (Error error) {
            runStat.stopThreadStat();

            throw error;
        } finally {
            try {
                /**
                 * [tRunJob_1 finally ] start
                 */

                currentComponent = "tRunJob_1";
                /**
                 * [tRunJob_1 finally ] stop
                 */
            } catch (Exception e) {
                //ignore
            } catch (Error error) {
                //ignore
            }
            resourceMap = null;
        }

        globalMap.put("tRunJob_1_SUBPROCESS_STATE", 1);
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
                String currentMethodName = new Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { //start the resume
                globalResumeTicket = true;

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
                Class jdbcclazz_tDBRow_1 = Class.forName(driverClass_tDBRow_1);

                String url_tDBRow_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "irsen";

                String dbUser_tDBRow_1 = "postgres";

                final String decryptedPassword_tDBRow_1 = PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:fRo3gUXoCocMl9dF87u0Nrjy3EeU6a+0krC5VQ=="
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

                query_tDBRow_1 = "UPDATE senateur set \"traitee\"=true";
                whetherReject_tDBRow_1 = false;
                globalMap.put("tDBRow_1_QUERY", query_tDBRow_1);
                try {
                    stmt_tDBRow_1.execute(query_tDBRow_1);
                } catch (Exception e) {
                    whetherReject_tDBRow_1 = true;

                    System.err.print(e.getMessage());
                    globalMap.put("tDBRow_1_ERROR_MESSAGE", e.getMessage());
                }

                if (!whetherReject_tDBRow_1) {}

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

                if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) && BundleUtils.inOSGi()) {
                    Class
                        .forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
                        .getMethod("checkedShutdown")
                        .invoke(null, (Object[]) null);
                }

                resourceMap.put("finish_tDBRow_1", true);

                ok_Hash.put("tDBRow_1", true);
                end_Hash.put("tDBRow_1", System.currentTimeMillis());

                if (execStat) {
                    runStat.updateStatOnConnection("OnComponentOk2", 0, "ok");
                }
                tDBRow_2Process(globalMap);
                /**
                 * [tDBRow_1 end ] stop
                 */
            } //end the resume
        } catch (Exception e) {
            TalendException te = new TalendException(e, currentComponent, globalMap);

            throw te;
        } catch (Error error) {
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
            } catch (Exception e) {
                //ignore
            } catch (Error error) {
                //ignore
            }
            resourceMap = null;
        }

        globalMap.put("tDBRow_1_SUBPROCESS_STATE", 1);
    }

    public void tDBRow_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
        globalMap.put("tDBRow_2_SUBPROCESS_STATE", 0);

        final boolean execStat = this.execStat;

        String iterateId = "";

        String currentComponent = "";
        java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

        try {
            // TDI-39566 avoid throwing an useless Exception
            boolean resumeIt = true;
            if (globalResumeTicket == false && resumeEntryMethodName != null) {
                String currentMethodName = new Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { //start the resume
                globalResumeTicket = true;

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
                Class jdbcclazz_tDBRow_2 = Class.forName(driverClass_tDBRow_2);

                String url_tDBRow_2 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "irsen";

                String dbUser_tDBRow_2 = "postgres";

                final String decryptedPassword_tDBRow_2 = PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:0eH+Zxz2FQbnKrA6Z/1+QgbPUzDf6cgE+oHqlA=="
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

                query_tDBRow_2 = "UPDATE senateur set \"date_traitement\"=now()";
                whetherReject_tDBRow_2 = false;
                globalMap.put("tDBRow_2_QUERY", query_tDBRow_2);
                try {
                    stmt_tDBRow_2.execute(query_tDBRow_2);
                } catch (Exception e) {
                    whetherReject_tDBRow_2 = true;

                    System.err.print(e.getMessage());
                    globalMap.put("tDBRow_2_ERROR_MESSAGE", e.getMessage());
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

                if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) && BundleUtils.inOSGi()) {
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
            } //end the resume
        } catch (Exception e) {
            TalendException te = new TalendException(e, currentComponent, globalMap);

            throw te;
        } catch (Error error) {
            runStat.stopThreadStat();

            throw error;
        } finally {
            try {
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
            } catch (Exception e) {
                //ignore
            } catch (Error error) {
                //ignore
            }
            resourceMap = null;
        }

        globalMap.put("tDBRow_2_SUBPROCESS_STATE", 1);
    }

    public static class row2Struct implements IPersistableComparableLookupRow<row2Struct> {

        static final byte[] commonByteArrayLock_IRSEN_Mise_A_Jour_Sen = new byte[0];
        static byte[] commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[0];
        protected static final int DEFAULT_HASHCODE = 1;
        protected static final int PRIME = 31;
        protected int hashCode = DEFAULT_HASHCODE;
        public boolean hashCodeDirty = true;

        public String loopKey;

        public long dptnum;

        public long getDptnum() {
            return this.dptnum;
        }

        public String dptcod;

        public String getDptcod() {
            return this.dptcod;
        }

        public String dptlib;

        public String getDptlib() {
            return this.dptlib;
        }

        public Long dptnbrsen;

        public Long getDptnbrsen() {
            return this.dptnbrsen;
        }

        public String dptmodscrsen;

        public String getDptmodscrsen() {
            return this.dptmodscrsen;
        }

        public String dptser;

        public String getDptser() {
            return this.dptser;
        }

        public String regcod;

        public String getRegcod() {
            return this.regcod;
        }

        public long dptnumtri;

        public long getDptnumtri() {
            return this.dptnumtri;
        }

        public String dptcodsirpas;

        public String getDptcodsirpas() {
            return this.dptcodsirpas;
        }

        public String dptlic;

        public String getDptlic() {
            return this.dptlic;
        }

        public String dptart;

        public String getDptart() {
            return this.dptart;
        }

        public String dptlibtri;

        public String getDptlibtri() {
            return this.dptlibtri;
        }

        public String temvalcod;

        public String getTemvalcod() {
            return this.temvalcod;
        }

        public String evelic;

        public String getEvelic() {
            return this.evelic;
        }

        public String evelib;

        public String getEvelib() {
            return this.evelib;
        }

        public String evelil;

        public String getEvelil() {
            return this.evelil;
        }

        public String eveobs;

        public String getEveobs() {
            return this.eveobs;
        }

        public String dptser2004;

        public String getDptser2004() {
            return this.dptser2004;
        }

        public String dptcmt;

        public String getDptcmt() {
            return this.dptcmt;
        }

        public String dptcmtaff;

        public String getDptcmtaff() {
            return this.dptcmtaff;
        }

        public Date dptdatdeb;

        public Date getDptdatdeb() {
            return this.dptdatdeb;
        }

        public Date dptdatfin;

        public Date getDptdatfin() {
            return this.dptdatfin;
        }

        public String evetempub;

        public String getEvetempub() {
            return this.evetempub;
        }

        public String dpturlcmp;

        public String getDpturlcmp() {
            return this.dpturlcmp;
        }

        public String dptminintcod;

        public String getDptminintcod() {
            return this.dptminintcod;
        }

        public Date syscredat;

        public Date getSyscredat() {
            return this.syscredat;
        }

        public String syscrelog;

        public String getSyscrelog() {
            return this.syscrelog;
        }

        public Date sysmajdat;

        public Date getSysmajdat() {
            return this.sysmajdat;
        }

        public String sysmajlog;

        public String getSysmajlog() {
            return this.sysmajlog;
        }

        @Override
        public int hashCode() {
            if (this.hashCodeDirty) {
                final int prime = PRIME;
                int result = DEFAULT_HASHCODE;

                result = prime * result + ((this.dptcod == null) ? 0 : this.dptcod.hashCode());

                this.hashCode = result;
                this.hashCodeDirty = false;
            }
            return this.hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            final row2Struct other = (row2Struct) obj;

            if (this.dptcod == null) {
                if (other.dptcod != null) return false;
            } else if (!this.dptcod.equals(other.dptcod)) return false;

            return true;
        }

        public void copyDataTo(row2Struct other) {
            other.dptnum = this.dptnum;
            other.dptcod = this.dptcod;
            other.dptlib = this.dptlib;
            other.dptnbrsen = this.dptnbrsen;
            other.dptmodscrsen = this.dptmodscrsen;
            other.dptser = this.dptser;
            other.regcod = this.regcod;
            other.dptnumtri = this.dptnumtri;
            other.dptcodsirpas = this.dptcodsirpas;
            other.dptlic = this.dptlic;
            other.dptart = this.dptart;
            other.dptlibtri = this.dptlibtri;
            other.temvalcod = this.temvalcod;
            other.evelic = this.evelic;
            other.evelib = this.evelib;
            other.evelil = this.evelil;
            other.eveobs = this.eveobs;
            other.dptser2004 = this.dptser2004;
            other.dptcmt = this.dptcmt;
            other.dptcmtaff = this.dptcmtaff;
            other.dptdatdeb = this.dptdatdeb;
            other.dptdatfin = this.dptdatfin;
            other.evetempub = this.evetempub;
            other.dpturlcmp = this.dpturlcmp;
            other.dptminintcod = this.dptminintcod;
            other.syscredat = this.syscredat;
            other.syscrelog = this.syscrelog;
            other.sysmajdat = this.sysmajdat;
            other.sysmajlog = this.sysmajlog;
        }

        public void copyKeysDataTo(row2Struct other) {
            other.dptcod = this.dptcod;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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

        private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                byte[] byteArray = new byte[length];
                dis.read(byteArray);
                strReturn = new String(byteArray, utf8Charset);
            }
            return strReturn;
        }

        private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            String strReturn = null;
            int length = 0;
            length = unmarshaller.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                byte[] byteArray = new byte[length];
                unmarshaller.read(byteArray);
                strReturn = new String(byteArray, utf8Charset);
            }
            return strReturn;
        }

        private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (str == null) {
                marshaller.writeInt(-1);
            } else {
                byte[] byteArray = str.getBytes(utf8Charset);
                marshaller.writeInt(byteArray.length);
                marshaller.write(byteArray);
            }
        }

        private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
            if (str == null) {
                dos.writeInt(-1);
            } else {
                byte[] byteArray = str.getBytes(utf8Charset);
                dos.writeInt(byteArray.length);
                dos.write(byteArray);
            }
        }

        private Date readDate(DataInputStream dis, ObjectInputStream ois) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = dis.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(dis.readLong());
            }
            return dateReturn;
        }

        private Date readDate(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            Date dateReturn = null;
            int length = 0;
            length = unmarshaller.readByte();
            if (length == -1) {
                dateReturn = null;
            } else {
                dateReturn = new Date(unmarshaller.readLong());
            }
            return dateReturn;
        }

        private void writeDate(Date date1, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
            if (date1 == null) {
                dos.writeByte(-1);
            } else {
                dos.writeByte(0);
                dos.writeLong(date1.getTime());
            }
        }

        private void writeDate(Date date1, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (date1 == null) {
                marshaller.writeByte(-1);
            } else {
                marshaller.writeByte(0);
                marshaller.writeLong(date1.getTime());
            }
        }

        public void readKeysData(ObjectInputStream dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
                try {
                    int length = 0;

                    this.dptcod = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
                try {
                    int length = 0;

                    this.dptcod = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void writeKeysData(ObjectOutputStream dos) {
            try {
                // String

                writeString(this.dptcod, dos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
            try {
                // String

                writeString(this.dptcod, dos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Fill Values data by reading ObjectInputStream.
         */
        public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
            try {
                int length = 0;

                this.dptnum = dis.readLong();

                this.dptlib = readString(dis, ois);

                length = dis.readByte();
                if (length == -1) {
                    this.dptnbrsen = null;
                } else {
                    this.dptnbrsen = dis.readLong();
                }

                this.dptmodscrsen = readString(dis, ois);

                this.dptser = readString(dis, ois);

                this.regcod = readString(dis, ois);

                this.dptnumtri = dis.readLong();

                this.dptcodsirpas = readString(dis, ois);

                this.dptlic = readString(dis, ois);

                this.dptart = readString(dis, ois);

                this.dptlibtri = readString(dis, ois);

                this.temvalcod = readString(dis, ois);

                this.evelic = readString(dis, ois);

                this.evelib = readString(dis, ois);

                this.evelil = readString(dis, ois);

                this.eveobs = readString(dis, ois);

                this.dptser2004 = readString(dis, ois);

                this.dptcmt = readString(dis, ois);

                this.dptcmtaff = readString(dis, ois);

                this.dptdatdeb = readDate(dis, ois);

                this.dptdatfin = readDate(dis, ois);

                this.evetempub = readString(dis, ois);

                this.dpturlcmp = readString(dis, ois);

                this.dptminintcod = readString(dis, ois);

                this.syscredat = readDate(dis, ois);

                this.syscrelog = readString(dis, ois);

                this.sysmajdat = readDate(dis, ois);

                this.sysmajlog = readString(dis, ois);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
            try {
                int length = 0;

                this.dptnum = objectIn.readLong();

                this.dptlib = readString(dis, objectIn);

                length = objectIn.readByte();
                if (length == -1) {
                    this.dptnbrsen = null;
                } else {
                    this.dptnbrsen = objectIn.readLong();
                }

                this.dptmodscrsen = readString(dis, objectIn);

                this.dptser = readString(dis, objectIn);

                this.regcod = readString(dis, objectIn);

                this.dptnumtri = objectIn.readLong();

                this.dptcodsirpas = readString(dis, objectIn);

                this.dptlic = readString(dis, objectIn);

                this.dptart = readString(dis, objectIn);

                this.dptlibtri = readString(dis, objectIn);

                this.temvalcod = readString(dis, objectIn);

                this.evelic = readString(dis, objectIn);

                this.evelib = readString(dis, objectIn);

                this.evelil = readString(dis, objectIn);

                this.eveobs = readString(dis, objectIn);

                this.dptser2004 = readString(dis, objectIn);

                this.dptcmt = readString(dis, objectIn);

                this.dptcmtaff = readString(dis, objectIn);

                this.dptdatdeb = readDate(dis, objectIn);

                this.dptdatfin = readDate(dis, objectIn);

                this.evetempub = readString(dis, objectIn);

                this.dpturlcmp = readString(dis, objectIn);

                this.dptminintcod = readString(dis, objectIn);

                this.syscredat = readDate(dis, objectIn);

                this.syscrelog = readString(dis, objectIn);

                this.sysmajdat = readDate(dis, objectIn);

                this.sysmajlog = readString(dis, objectIn);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Return a byte array which represents Values data.
         */
        public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
            try {
                dos.writeLong(this.dptnum);

                writeString(this.dptlib, dos, oos);

                if (this.dptnbrsen == null) {
                    dos.writeByte(-1);
                } else {
                    dos.writeByte(0);
                    dos.writeLong(this.dptnbrsen);
                }

                writeString(this.dptmodscrsen, dos, oos);

                writeString(this.dptser, dos, oos);

                writeString(this.regcod, dos, oos);

                dos.writeLong(this.dptnumtri);

                writeString(this.dptcodsirpas, dos, oos);

                writeString(this.dptlic, dos, oos);

                writeString(this.dptart, dos, oos);

                writeString(this.dptlibtri, dos, oos);

                writeString(this.temvalcod, dos, oos);

                writeString(this.evelic, dos, oos);

                writeString(this.evelib, dos, oos);

                writeString(this.evelil, dos, oos);

                writeString(this.eveobs, dos, oos);

                writeString(this.dptser2004, dos, oos);

                writeString(this.dptcmt, dos, oos);

                writeString(this.dptcmtaff, dos, oos);

                writeDate(this.dptdatdeb, dos, oos);

                writeDate(this.dptdatfin, dos, oos);

                writeString(this.evetempub, dos, oos);

                writeString(this.dpturlcmp, dos, oos);

                writeString(this.dptminintcod, dos, oos);

                writeDate(this.syscredat, dos, oos);

                writeString(this.syscrelog, dos, oos);

                writeDate(this.sysmajdat, dos, oos);

                writeString(this.sysmajlog, dos, oos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
            try {
                objectOut.writeLong(this.dptnum);

                writeString(this.dptlib, dos, objectOut);

                if (this.dptnbrsen == null) {
                    objectOut.writeByte(-1);
                } else {
                    objectOut.writeByte(0);
                    objectOut.writeLong(this.dptnbrsen);
                }

                writeString(this.dptmodscrsen, dos, objectOut);

                writeString(this.dptser, dos, objectOut);

                writeString(this.regcod, dos, objectOut);

                objectOut.writeLong(this.dptnumtri);

                writeString(this.dptcodsirpas, dos, objectOut);

                writeString(this.dptlic, dos, objectOut);

                writeString(this.dptart, dos, objectOut);

                writeString(this.dptlibtri, dos, objectOut);

                writeString(this.temvalcod, dos, objectOut);

                writeString(this.evelic, dos, objectOut);

                writeString(this.evelib, dos, objectOut);

                writeString(this.evelil, dos, objectOut);

                writeString(this.eveobs, dos, objectOut);

                writeString(this.dptser2004, dos, objectOut);

                writeString(this.dptcmt, dos, objectOut);

                writeString(this.dptcmtaff, dos, objectOut);

                writeDate(this.dptdatdeb, dos, objectOut);

                writeDate(this.dptdatfin, dos, objectOut);

                writeString(this.evetempub, dos, objectOut);

                writeString(this.dpturlcmp, dos, objectOut);

                writeString(this.dptminintcod, dos, objectOut);

                writeDate(this.syscredat, dos, objectOut);

                writeString(this.syscrelog, dos, objectOut);

                writeDate(this.sysmajdat, dos, objectOut);

                writeString(this.sysmajlog, dos, objectOut);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean supportMarshaller() {
            return true;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append("[");
            sb.append("dptnum=" + String.valueOf(dptnum));
            sb.append(",dptcod=" + dptcod);
            sb.append(",dptlib=" + dptlib);
            sb.append(",dptnbrsen=" + String.valueOf(dptnbrsen));
            sb.append(",dptmodscrsen=" + dptmodscrsen);
            sb.append(",dptser=" + dptser);
            sb.append(",regcod=" + regcod);
            sb.append(",dptnumtri=" + String.valueOf(dptnumtri));
            sb.append(",dptcodsirpas=" + dptcodsirpas);
            sb.append(",dptlic=" + dptlic);
            sb.append(",dptart=" + dptart);
            sb.append(",dptlibtri=" + dptlibtri);
            sb.append(",temvalcod=" + temvalcod);
            sb.append(",evelic=" + evelic);
            sb.append(",evelib=" + evelib);
            sb.append(",evelil=" + evelil);
            sb.append(",eveobs=" + eveobs);
            sb.append(",dptser2004=" + dptser2004);
            sb.append(",dptcmt=" + dptcmt);
            sb.append(",dptcmtaff=" + dptcmtaff);
            sb.append(",dptdatdeb=" + String.valueOf(dptdatdeb));
            sb.append(",dptdatfin=" + String.valueOf(dptdatfin));
            sb.append(",evetempub=" + evetempub);
            sb.append(",dpturlcmp=" + dpturlcmp);
            sb.append(",dptminintcod=" + dptminintcod);
            sb.append(",syscredat=" + String.valueOf(syscredat));
            sb.append(",syscrelog=" + syscrelog);
            sb.append(",sysmajdat=" + String.valueOf(sysmajdat));
            sb.append(",sysmajlog=" + sysmajlog);
            sb.append("]");

            return sb.toString();
        }

        /**
         * Compare keys
         */
        public int compareTo(row2Struct other) {
            int returnValue = -1;

            returnValue = checkNullsAndCompare(this.dptcod, other.dptcod);
            if (returnValue != 0) {
                return returnValue;
            }

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

    public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
        globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

        final boolean execStat = this.execStat;

        String iterateId = "";

        String currentComponent = "";
        java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

        try {
            // TDI-39566 avoid throwing an useless Exception
            boolean resumeIt = true;
            if (globalResumeTicket == false && resumeEntryMethodName != null) {
                String currentMethodName = new Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { //start the resume
                globalResumeTicket = true;

                row2Struct row2 = new row2Struct();

                /**
                 * [tAdvancedHash_row2 begin ] start
                 */

                ok_Hash.put("tAdvancedHash_row2", false);
                start_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());

                currentComponent = "tAdvancedHash_row2";

                if (execStat) {
                    runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
                }

                int tos_count_tAdvancedHash_row2 = 0;

                // connection name:row2
                // source node:tDBInput_2 - inputs:(after_tDBInput_1) outputs:(row2,row2) | target node:tAdvancedHash_row2 - inputs:(row2) outputs:()
                // linked node: tMap_1 - inputs:(row1,row2,row3) outputs:(LOGS,SEN_MAJ)

                org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row2 =
                    org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

                org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.<row2Struct>getLookup(
                    matchingModeEnum_row2
                );

                globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);

                /**
                 * [tAdvancedHash_row2 begin ] stop
                 */

                /**
                 * [tDBInput_2 begin ] start
                 */

                ok_Hash.put("tDBInput_2", false);
                start_Hash.put("tDBInput_2", System.currentTimeMillis());

                currentComponent = "tDBInput_2";

                int tos_count_tDBInput_2 = 0;

                int nb_line_tDBInput_2 = 0;
                java.sql.Connection conn_tDBInput_2 = null;
                String driverClass_tDBInput_2 = "org.postgresql.Driver";
                Class jdbcclazz_tDBInput_2 = Class.forName(driverClass_tDBInput_2);
                String dbUser_tDBInput_2 = "pgdba";

                final String decryptedPassword_tDBInput_2 = PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:ULyTjKH8XTC9OZeiMwpPGWkQBguB617yCchhyTCaezdo3CvY9BFnsQ=="
                );

                String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;

                String url_tDBInput_2 = "jdbc:postgresql://" + "brandy-test.senat.fr" + ":" + "5603" + "/" + "sendevrep2";

                conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2, dbUser_tDBInput_2, dbPwd_tDBInput_2);

                conn_tDBInput_2.setAutoCommit(false);

                java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

                String dbquery_tDBInput_2 = "Select * from SENATEURS.DPT where temvalcod='ACTIF'";

                globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);
                java.sql.ResultSet rs_tDBInput_2 = null;

                try {
                    rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
                    java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
                    int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

                    String tmpContent_tDBInput_2 = null;

                    while (rs_tDBInput_2.next()) {
                        nb_line_tDBInput_2++;

                        if (colQtyInRs_tDBInput_2 < 1) {
                            row2.dptnum = 0;
                        } else {
                            row2.dptnum = rs_tDBInput_2.getLong(1);
                            if (rs_tDBInput_2.wasNull()) {
                                throw new RuntimeException("Null value in non-Nullable column");
                            }
                        }
                        if (colQtyInRs_tDBInput_2 < 2) {
                            row2.dptcod = null;
                        } else {
                            row2.dptcod = JDBCUtil.getString(rs_tDBInput_2, 2, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 3) {
                            row2.dptlib = null;
                        } else {
                            row2.dptlib = JDBCUtil.getString(rs_tDBInput_2, 3, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 4) {
                            row2.dptnbrsen = null;
                        } else {
                            row2.dptnbrsen = rs_tDBInput_2.getLong(4);
                            if (rs_tDBInput_2.wasNull()) {
                                row2.dptnbrsen = null;
                            }
                        }
                        if (colQtyInRs_tDBInput_2 < 5) {
                            row2.dptmodscrsen = null;
                        } else {
                            row2.dptmodscrsen = JDBCUtil.getString(rs_tDBInput_2, 5, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 6) {
                            row2.dptser = null;
                        } else {
                            row2.dptser = JDBCUtil.getString(rs_tDBInput_2, 6, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 7) {
                            row2.regcod = null;
                        } else {
                            row2.regcod = JDBCUtil.getString(rs_tDBInput_2, 7, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 8) {
                            row2.dptnumtri = 0;
                        } else {
                            row2.dptnumtri = rs_tDBInput_2.getLong(8);
                            if (rs_tDBInput_2.wasNull()) {
                                throw new RuntimeException("Null value in non-Nullable column");
                            }
                        }
                        if (colQtyInRs_tDBInput_2 < 9) {
                            row2.dptcodsirpas = null;
                        } else {
                            row2.dptcodsirpas = JDBCUtil.getString(rs_tDBInput_2, 9, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 10) {
                            row2.dptlic = null;
                        } else {
                            row2.dptlic = JDBCUtil.getString(rs_tDBInput_2, 10, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 11) {
                            row2.dptart = null;
                        } else {
                            row2.dptart = JDBCUtil.getString(rs_tDBInput_2, 11, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 12) {
                            row2.dptlibtri = null;
                        } else {
                            row2.dptlibtri = JDBCUtil.getString(rs_tDBInput_2, 12, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 13) {
                            row2.temvalcod = null;
                        } else {
                            row2.temvalcod = JDBCUtil.getString(rs_tDBInput_2, 13, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 14) {
                            row2.evelic = null;
                        } else {
                            row2.evelic = JDBCUtil.getString(rs_tDBInput_2, 14, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 15) {
                            row2.evelib = null;
                        } else {
                            row2.evelib = JDBCUtil.getString(rs_tDBInput_2, 15, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 16) {
                            row2.evelil = null;
                        } else {
                            row2.evelil = JDBCUtil.getString(rs_tDBInput_2, 16, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 17) {
                            row2.eveobs = null;
                        } else {
                            row2.eveobs = JDBCUtil.getString(rs_tDBInput_2, 17, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 18) {
                            row2.dptser2004 = null;
                        } else {
                            row2.dptser2004 = JDBCUtil.getString(rs_tDBInput_2, 18, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 19) {
                            row2.dptcmt = null;
                        } else {
                            row2.dptcmt = JDBCUtil.getString(rs_tDBInput_2, 19, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 20) {
                            row2.dptcmtaff = null;
                        } else {
                            row2.dptcmtaff = JDBCUtil.getString(rs_tDBInput_2, 20, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 21) {
                            row2.dptdatdeb = null;
                        } else {
                            row2.dptdatdeb = JDBCUtil.getDate(rs_tDBInput_2, 21);
                        }
                        if (colQtyInRs_tDBInput_2 < 22) {
                            row2.dptdatfin = null;
                        } else {
                            row2.dptdatfin = JDBCUtil.getDate(rs_tDBInput_2, 22);
                        }
                        if (colQtyInRs_tDBInput_2 < 23) {
                            row2.evetempub = null;
                        } else {
                            row2.evetempub = JDBCUtil.getString(rs_tDBInput_2, 23, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 24) {
                            row2.dpturlcmp = null;
                        } else {
                            row2.dpturlcmp = JDBCUtil.getString(rs_tDBInput_2, 24, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 25) {
                            row2.dptminintcod = null;
                        } else {
                            row2.dptminintcod = JDBCUtil.getString(rs_tDBInput_2, 25, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 26) {
                            row2.syscredat = null;
                        } else {
                            row2.syscredat = JDBCUtil.getDate(rs_tDBInput_2, 26);
                        }
                        if (colQtyInRs_tDBInput_2 < 27) {
                            row2.syscrelog = null;
                        } else {
                            row2.syscrelog = JDBCUtil.getString(rs_tDBInput_2, 27, false);
                        }
                        if (colQtyInRs_tDBInput_2 < 28) {
                            row2.sysmajdat = null;
                        } else {
                            row2.sysmajdat = JDBCUtil.getDate(rs_tDBInput_2, 28);
                        }
                        if (colQtyInRs_tDBInput_2 < 29) {
                            row2.sysmajlog = null;
                        } else {
                            row2.sysmajlog = JDBCUtil.getString(rs_tDBInput_2, 29, false);
                        }

                        /**
                         * [tDBInput_2 begin ] stop
                         */

                        /**
                         * [tDBInput_2 main ] start
                         */

                        currentComponent = "tDBInput_2";

                        tos_count_tDBInput_2++;

                        /**
                         * [tDBInput_2 main ] stop
                         */

                        /**
                         * [tDBInput_2 process_data_begin ] start
                         */

                        currentComponent = "tDBInput_2";

                        /**
                         * [tDBInput_2 process_data_begin ] stop
                         */

                        /**
                         * [tAdvancedHash_row2 main ] start
                         */

                        currentComponent = "tAdvancedHash_row2";

                        if (execStat) {
                            runStat.updateStatOnConnection(iterateId, 1, 1, "row2");
                        }

                        row2Struct row2_HashRow = new row2Struct();

                        row2_HashRow.dptnum = row2.dptnum;

                        row2_HashRow.dptcod = row2.dptcod;

                        row2_HashRow.dptlib = row2.dptlib;

                        row2_HashRow.dptnbrsen = row2.dptnbrsen;

                        row2_HashRow.dptmodscrsen = row2.dptmodscrsen;

                        row2_HashRow.dptser = row2.dptser;

                        row2_HashRow.regcod = row2.regcod;

                        row2_HashRow.dptnumtri = row2.dptnumtri;

                        row2_HashRow.dptcodsirpas = row2.dptcodsirpas;

                        row2_HashRow.dptlic = row2.dptlic;

                        row2_HashRow.dptart = row2.dptart;

                        row2_HashRow.dptlibtri = row2.dptlibtri;

                        row2_HashRow.temvalcod = row2.temvalcod;

                        row2_HashRow.evelic = row2.evelic;

                        row2_HashRow.evelib = row2.evelib;

                        row2_HashRow.evelil = row2.evelil;

                        row2_HashRow.eveobs = row2.eveobs;

                        row2_HashRow.dptser2004 = row2.dptser2004;

                        row2_HashRow.dptcmt = row2.dptcmt;

                        row2_HashRow.dptcmtaff = row2.dptcmtaff;

                        row2_HashRow.dptdatdeb = row2.dptdatdeb;

                        row2_HashRow.dptdatfin = row2.dptdatfin;

                        row2_HashRow.evetempub = row2.evetempub;

                        row2_HashRow.dpturlcmp = row2.dpturlcmp;

                        row2_HashRow.dptminintcod = row2.dptminintcod;

                        row2_HashRow.syscredat = row2.syscredat;

                        row2_HashRow.syscrelog = row2.syscrelog;

                        row2_HashRow.sysmajdat = row2.sysmajdat;

                        row2_HashRow.sysmajlog = row2.sysmajlog;

                        tHash_Lookup_row2.put(row2_HashRow);

                        tos_count_tAdvancedHash_row2++;

                        /**
                         * [tAdvancedHash_row2 main ] stop
                         */

                        /**
                         * [tAdvancedHash_row2 process_data_begin ] start
                         */

                        currentComponent = "tAdvancedHash_row2";

                        /**
                         * [tAdvancedHash_row2 process_data_begin ] stop
                         */

                        /**
                         * [tAdvancedHash_row2 process_data_end ] start
                         */

                        currentComponent = "tAdvancedHash_row2";

                        /**
                         * [tAdvancedHash_row2 process_data_end ] stop
                         */

                        /**
                         * [tDBInput_2 process_data_end ] start
                         */

                        currentComponent = "tDBInput_2";

                        /**
                         * [tDBInput_2 process_data_end ] stop
                         */

                        /**
                         * [tDBInput_2 end ] start
                         */

                        currentComponent = "tDBInput_2";
                    }
                } finally {
                    if (rs_tDBInput_2 != null) {
                        rs_tDBInput_2.close();
                    }
                    if (stmt_tDBInput_2 != null) {
                        stmt_tDBInput_2.close();
                    }
                    if (conn_tDBInput_2 != null && !conn_tDBInput_2.isClosed()) {
                        conn_tDBInput_2.commit();

                        conn_tDBInput_2.close();

                        if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) && BundleUtils.inOSGi()) {
                            Class
                                .forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
                                .getMethod("checkedShutdown")
                                .invoke(null, (Object[]) null);
                        }
                    }
                }
                globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);

                ok_Hash.put("tDBInput_2", true);
                end_Hash.put("tDBInput_2", System.currentTimeMillis());

                /**
                 * [tDBInput_2 end ] stop
                 */

                /**
                 * [tAdvancedHash_row2 end ] start
                 */

                currentComponent = "tAdvancedHash_row2";

                tHash_Lookup_row2.endPut();

                if (execStat) {
                    runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
                }

                ok_Hash.put("tAdvancedHash_row2", true);
                end_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());
                /**
                 * [tAdvancedHash_row2 end ] stop
                 */

            } //end the resume
        } catch (Exception e) {
            TalendException te = new TalendException(e, currentComponent, globalMap);

            throw te;
        } catch (Error error) {
            runStat.stopThreadStat();

            throw error;
        } finally {
            try {
                /**
                 * [tDBInput_2 finally ] start
                 */

                currentComponent = "tDBInput_2";

                /**
                 * [tDBInput_2 finally ] stop
                 */

                /**
                 * [tAdvancedHash_row2 finally ] start
                 */

                currentComponent = "tAdvancedHash_row2";
                /**
                 * [tAdvancedHash_row2 finally ] stop
                 */

            } catch (Exception e) {
                //ignore
            } catch (Error error) {
                //ignore
            }
            resourceMap = null;
        }

        globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
    }

    public static class row3Struct implements IPersistableComparableLookupRow<row3Struct> {

        static final byte[] commonByteArrayLock_IRSEN_Mise_A_Jour_Sen = new byte[0];
        static byte[] commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[0];
        protected static final int DEFAULT_HASHCODE = 1;
        protected static final int PRIME = 31;
        protected int hashCode = DEFAULT_HASHCODE;
        public boolean hashCodeDirty = true;

        public String loopKey;

        public String pcscod;

        public String getPcscod() {
            return this.pcscod;
        }

        public String pcslil;

        public String getPcslil() {
            return this.pcslil;
        }

        public String pcs42cod;

        public String getPcs42cod() {
            return this.pcs42cod;
        }

        @Override
        public int hashCode() {
            if (this.hashCodeDirty) {
                final int prime = PRIME;
                int result = DEFAULT_HASHCODE;

                result = prime * result + ((this.pcs42cod == null) ? 0 : this.pcs42cod.hashCode());

                this.hashCode = result;
                this.hashCodeDirty = false;
            }
            return this.hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            final row3Struct other = (row3Struct) obj;

            if (this.pcs42cod == null) {
                if (other.pcs42cod != null) return false;
            } else if (!this.pcs42cod.equals(other.pcs42cod)) return false;

            return true;
        }

        public void copyDataTo(row3Struct other) {
            other.pcscod = this.pcscod;
            other.pcslil = this.pcslil;
            other.pcs42cod = this.pcs42cod;
        }

        public void copyKeysDataTo(row3Struct other) {
            other.pcs42cod = this.pcs42cod;
        }

        private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                byte[] byteArray = new byte[length];
                dis.read(byteArray);
                strReturn = new String(byteArray, utf8Charset);
            }
            return strReturn;
        }

        private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
            String strReturn = null;
            int length = 0;
            length = unmarshaller.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                byte[] byteArray = new byte[length];
                unmarshaller.read(byteArray);
                strReturn = new String(byteArray, utf8Charset);
            }
            return strReturn;
        }

        private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller) throws IOException {
            if (str == null) {
                marshaller.writeInt(-1);
            } else {
                byte[] byteArray = str.getBytes(utf8Charset);
                marshaller.writeInt(byteArray.length);
                marshaller.write(byteArray);
            }
        }

        private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
            if (str == null) {
                dos.writeInt(-1);
            } else {
                byte[] byteArray = str.getBytes(utf8Charset);
                dos.writeInt(byteArray.length);
                dos.write(byteArray);
            }
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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
                if (length > commonByteArray_IRSEN_Mise_A_Jour_Sen.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Mise_A_Jour_Sen.length == 0) {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Mise_A_Jour_Sen = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Mise_A_Jour_Sen, 0, length, utf8Charset);
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

        public void readKeysData(ObjectInputStream dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
                try {
                    int length = 0;

                    this.pcs42cod = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Mise_A_Jour_Sen) {
                try {
                    int length = 0;

                    this.pcs42cod = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void writeKeysData(ObjectOutputStream dos) {
            try {
                // String

                writeString(this.pcs42cod, dos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
            try {
                // String

                writeString(this.pcs42cod, dos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Fill Values data by reading ObjectInputStream.
         */
        public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
            try {
                int length = 0;

                this.pcscod = readString(dis, ois);

                this.pcslil = readString(dis, ois);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
            try {
                int length = 0;

                this.pcscod = readString(dis, objectIn);

                this.pcslil = readString(dis, objectIn);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Return a byte array which represents Values data.
         */
        public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
            try {
                writeString(this.pcscod, dos, oos);

                writeString(this.pcslil, dos, oos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
            try {
                writeString(this.pcscod, dos, objectOut);

                writeString(this.pcslil, dos, objectOut);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean supportMarshaller() {
            return true;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append("[");
            sb.append("pcscod=" + pcscod);
            sb.append(",pcslil=" + pcslil);
            sb.append(",pcs42cod=" + pcs42cod);
            sb.append("]");

            return sb.toString();
        }

        /**
         * Compare keys
         */
        public int compareTo(row3Struct other) {
            int returnValue = -1;

            returnValue = checkNullsAndCompare(this.pcs42cod, other.pcs42cod);
            if (returnValue != 0) {
                return returnValue;
            }

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

    public void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
        globalMap.put("tDBInput_3_SUBPROCESS_STATE", 0);

        final boolean execStat = this.execStat;

        String iterateId = "";

        String currentComponent = "";
        java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

        try {
            // TDI-39566 avoid throwing an useless Exception
            boolean resumeIt = true;
            if (globalResumeTicket == false && resumeEntryMethodName != null) {
                String currentMethodName = new Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { //start the resume
                globalResumeTicket = true;

                row3Struct row3 = new row3Struct();

                /**
                 * [tAdvancedHash_row3 begin ] start
                 */

                ok_Hash.put("tAdvancedHash_row3", false);
                start_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());

                currentComponent = "tAdvancedHash_row3";

                if (execStat) {
                    runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row3");
                }

                int tos_count_tAdvancedHash_row3 = 0;

                // connection name:row3
                // source node:tDBInput_3 - inputs:(after_tDBInput_1) outputs:(row3,row3) | target node:tAdvancedHash_row3 - inputs:(row3) outputs:()
                // linked node: tMap_1 - inputs:(row1,row2,row3) outputs:(LOGS,SEN_MAJ)

                org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row3 =
                    org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

                org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup.<row3Struct>getLookup(
                    matchingModeEnum_row3
                );

                globalMap.put("tHash_Lookup_row3", tHash_Lookup_row3);

                /**
                 * [tAdvancedHash_row3 begin ] stop
                 */

                /**
                 * [tDBInput_3 begin ] start
                 */

                ok_Hash.put("tDBInput_3", false);
                start_Hash.put("tDBInput_3", System.currentTimeMillis());

                currentComponent = "tDBInput_3";

                int tos_count_tDBInput_3 = 0;

                int nb_line_tDBInput_3 = 0;
                java.sql.Connection conn_tDBInput_3 = null;
                String driverClass_tDBInput_3 = "org.postgresql.Driver";
                Class jdbcclazz_tDBInput_3 = Class.forName(driverClass_tDBInput_3);
                String dbUser_tDBInput_3 = "pgdba";

                final String decryptedPassword_tDBInput_3 = PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:HlpKe63x7AdGm9VVTmxPJD1LQoMDoodLgodRQ1JtwODxyYvrfcYgdA=="
                );

                String dbPwd_tDBInput_3 = decryptedPassword_tDBInput_3;

                String url_tDBInput_3 = "jdbc:postgresql://" + "brandy-test.senat.fr" + ":" + "5603" + "/" + "sendevrep2";

                conn_tDBInput_3 = java.sql.DriverManager.getConnection(url_tDBInput_3, dbUser_tDBInput_3, dbPwd_tDBInput_3);

                conn_tDBInput_3.setAutoCommit(false);

                java.sql.Statement stmt_tDBInput_3 = conn_tDBInput_3.createStatement();

                String dbquery_tDBInput_3 = "Select * from senateurs.pcs";

                globalMap.put("tDBInput_3_QUERY", dbquery_tDBInput_3);
                java.sql.ResultSet rs_tDBInput_3 = null;

                try {
                    rs_tDBInput_3 = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
                    java.sql.ResultSetMetaData rsmd_tDBInput_3 = rs_tDBInput_3.getMetaData();
                    int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();

                    String tmpContent_tDBInput_3 = null;

                    while (rs_tDBInput_3.next()) {
                        nb_line_tDBInput_3++;

                        if (colQtyInRs_tDBInput_3 < 1) {
                            row3.pcscod = null;
                        } else {
                            row3.pcscod = JDBCUtil.getString(rs_tDBInput_3, 1, false);
                        }
                        if (colQtyInRs_tDBInput_3 < 2) {
                            row3.pcslil = null;
                        } else {
                            row3.pcslil = JDBCUtil.getString(rs_tDBInput_3, 2, false);
                        }
                        if (colQtyInRs_tDBInput_3 < 3) {
                            row3.pcs42cod = null;
                        } else {
                            row3.pcs42cod = JDBCUtil.getString(rs_tDBInput_3, 3, false);
                        }

                        /**
                         * [tDBInput_3 begin ] stop
                         */

                        /**
                         * [tDBInput_3 main ] start
                         */

                        currentComponent = "tDBInput_3";

                        tos_count_tDBInput_3++;

                        /**
                         * [tDBInput_3 main ] stop
                         */

                        /**
                         * [tDBInput_3 process_data_begin ] start
                         */

                        currentComponent = "tDBInput_3";

                        /**
                         * [tDBInput_3 process_data_begin ] stop
                         */

                        /**
                         * [tAdvancedHash_row3 main ] start
                         */

                        currentComponent = "tAdvancedHash_row3";

                        if (execStat) {
                            runStat.updateStatOnConnection(iterateId, 1, 1, "row3");
                        }

                        row3Struct row3_HashRow = new row3Struct();

                        row3_HashRow.pcscod = row3.pcscod;

                        row3_HashRow.pcslil = row3.pcslil;

                        row3_HashRow.pcs42cod = row3.pcs42cod;

                        tHash_Lookup_row3.put(row3_HashRow);

                        tos_count_tAdvancedHash_row3++;

                        /**
                         * [tAdvancedHash_row3 main ] stop
                         */

                        /**
                         * [tAdvancedHash_row3 process_data_begin ] start
                         */

                        currentComponent = "tAdvancedHash_row3";

                        /**
                         * [tAdvancedHash_row3 process_data_begin ] stop
                         */

                        /**
                         * [tAdvancedHash_row3 process_data_end ] start
                         */

                        currentComponent = "tAdvancedHash_row3";

                        /**
                         * [tAdvancedHash_row3 process_data_end ] stop
                         */

                        /**
                         * [tDBInput_3 process_data_end ] start
                         */

                        currentComponent = "tDBInput_3";

                        /**
                         * [tDBInput_3 process_data_end ] stop
                         */

                        /**
                         * [tDBInput_3 end ] start
                         */

                        currentComponent = "tDBInput_3";
                    }
                } finally {
                    if (rs_tDBInput_3 != null) {
                        rs_tDBInput_3.close();
                    }
                    if (stmt_tDBInput_3 != null) {
                        stmt_tDBInput_3.close();
                    }
                    if (conn_tDBInput_3 != null && !conn_tDBInput_3.isClosed()) {
                        conn_tDBInput_3.commit();

                        conn_tDBInput_3.close();

                        if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) && BundleUtils.inOSGi()) {
                            Class
                                .forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
                                .getMethod("checkedShutdown")
                                .invoke(null, (Object[]) null);
                        }
                    }
                }
                globalMap.put("tDBInput_3_NB_LINE", nb_line_tDBInput_3);

                ok_Hash.put("tDBInput_3", true);
                end_Hash.put("tDBInput_3", System.currentTimeMillis());

                /**
                 * [tDBInput_3 end ] stop
                 */

                /**
                 * [tAdvancedHash_row3 end ] start
                 */

                currentComponent = "tAdvancedHash_row3";

                tHash_Lookup_row3.endPut();

                if (execStat) {
                    runStat.updateStat(resourceMap, iterateId, 2, 0, "row3");
                }

                ok_Hash.put("tAdvancedHash_row3", true);
                end_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());
                /**
                 * [tAdvancedHash_row3 end ] stop
                 */

            } //end the resume
        } catch (Exception e) {
            TalendException te = new TalendException(e, currentComponent, globalMap);

            throw te;
        } catch (Error error) {
            runStat.stopThreadStat();

            throw error;
        } finally {
            try {
                /**
                 * [tDBInput_3 finally ] start
                 */

                currentComponent = "tDBInput_3";

                /**
                 * [tDBInput_3 finally ] stop
                 */

                /**
                 * [tAdvancedHash_row3 finally ] start
                 */

                currentComponent = "tAdvancedHash_row3";
                /**
                 * [tAdvancedHash_row3 finally ] stop
                 */

            } catch (Exception e) {
                //ignore
            } catch (Error error) {
                //ignore
            }
            resourceMap = null;
        }

        globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
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
        final Mise_A_Jour_Sen Mise_A_Jour_SenClass = new Mise_A_Jour_Sen();

        int exitCode = Mise_A_Jour_SenClass.runJobInTOS(args);

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
        boolean inOSGi = BundleUtils.inOSGi();

        if (inOSGi) {
            java.util.Dictionary<String, Object> jobProperties = BundleUtils.getJobProperties(jobName);

            if (jobProperties != null && jobProperties.get("context") != null) {
                contextStr = (String) jobProperties.get("context");
            }
        }

        try {
            //call job/subjob with an existing context, like: --context=production. if without this parameter, there will use the default context instead.
            java.io.InputStream inContext =
                Mise_A_Jour_Sen.class.getClassLoader()
                    .getResourceAsStream("irsen/mise_a_jour_sen_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = Mise_A_Jour_Sen.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
            }
            if (inContext != null) {
                try {
                    //defaultProps is in order to keep the original context value
                    if (context != null && context.isEmpty()) {
                        defaultProps.load(inContext);
                        context = new ContextProperties(defaultProps);
                    }
                } finally {
                    inContext.close();
                }
            } else if (!isDefaultContext) {
                //print info and job continue to run, for case: context_param is not empty.
                System.err.println("Could not find the context " + contextStr);
            }

            if (!context_param.isEmpty()) {
                context.putAll(context_param);
                //set types for params from parentJobs
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
        } catch (IOException ie) {
            System.err.println("Could not load context " + contextStr);
            ie.printStackTrace();
        }

        // get context value from parent directly
        if (parentContextMap != null && !parentContextMap.isEmpty()) {}

        //Resume: init the resumeUtil
        resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
        resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
        resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

        List<String> parametersToEncrypt = new java.util.ArrayList<String>();
        //Resume: jobStart
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
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
        globalMap.put("concurrentHashMap", concurrentHashMap);

        long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endUsedMemory = 0;
        long end = 0;

        startTime = System.currentTimeMillis();

        this.globalResumeTicket = true; //to run tPreJob

        this.globalResumeTicket = false; //to run others jobs

        try {
            errorCode = null;
            tDBInput_1Process(globalMap);
            if (!"failure".equals(status)) {
                status = "end";
            }
        } catch (TalendException e_tDBInput_1) {
            globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

            e_tDBInput_1.printStackTrace();
        }

        this.globalResumeTicket = true; //to run tPostJob

        end = System.currentTimeMillis();

        if (watch) {
            System.out.println((end - startTime) + " milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : Mise_A_Jour_Sen");
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
        } else if (arg.startsWith("--audit.enabled") && arg.contains("=")) { //for trunjob call
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
            // if the left string doesn't include escape chars, append the left into the result
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
 *     185292 characters generated by Talend Open Studio for Data Integration
 *     on the 13 janvier 2022 à 15:30:37 CET
 ************************************************************************************************/
