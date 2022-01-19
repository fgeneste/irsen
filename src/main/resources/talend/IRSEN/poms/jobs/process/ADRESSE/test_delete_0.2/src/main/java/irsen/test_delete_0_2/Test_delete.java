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

package irsen.test_delete_0_2;

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

    private final String jobVersion = "0.2";
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

    public void tDBInput_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tFlowToIterate_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
        throws TalendException {
        end_Hash.put(errorComponent, System.currentTimeMillis());

        status = "failure";

        tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
    }

    public void tDBRow_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
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

        public java.util.Date SENDATNAI;

        public java.util.Date getSENDATNAI() {
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
                String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { // start the resume
                globalResumeTicket = true;

                row1Struct row1 = new row1Struct();

                /**
                 * [tFlowToIterate_1 begin ] start
                 */

                int NB_ITERATE_tDBRow_2 = 0; // for statistics

                ok_Hash.put("tFlowToIterate_1", false);
                start_Hash.put("tFlowToIterate_1", System.currentTimeMillis());

                currentComponent = "tFlowToIterate_1";

                int tos_count_tFlowToIterate_1 = 0;

                int nb_line_tFlowToIterate_1 = 0;
                int counter_tFlowToIterate_1 = 0;

                /**
                 * [tFlowToIterate_1 begin ] stop
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
                java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
                String dbUser_tDBInput_1 = "postgres";

                final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:ZL5IqduuDQNKRyINzxwMRcasE5GOTFUKiVouEQ=="
                );

                String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

                String url_tDBInput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "irsen";

                conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1, dbPwd_tDBInput_1);

                conn_tDBInput_1.setAutoCommit(false);

                java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

                String dbquery_tDBInput_1 = "Select * from sen";

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
                            row1.SENMAT = routines.system.JDBCUtil.getString(rs_tDBInput_1, 1, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 2) {
                            row1.QUACOD = null;
                        } else {
                            row1.QUACOD = routines.system.JDBCUtil.getString(rs_tDBInput_1, 2, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 3) {
                            row1.SENNOMUSE = null;
                        } else {
                            row1.SENNOMUSE = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 4) {
                            row1.SENNOMPAT = null;
                        } else {
                            row1.SENNOMPAT = routines.system.JDBCUtil.getString(rs_tDBInput_1, 4, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 5) {
                            row1.SENNOMMAR = null;
                        } else {
                            row1.SENNOMMAR = routines.system.JDBCUtil.getString(rs_tDBInput_1, 5, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 6) {
                            row1.SENNOMTEC = null;
                        } else {
                            row1.SENNOMTEC = routines.system.JDBCUtil.getString(rs_tDBInput_1, 6, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 7) {
                            row1.SENPRENOMUSE = null;
                        } else {
                            row1.SENPRENOMUSE = routines.system.JDBCUtil.getString(rs_tDBInput_1, 7, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 8) {
                            row1.SENPRENOMCIV = null;
                        } else {
                            row1.SENPRENOMCIV = routines.system.JDBCUtil.getString(rs_tDBInput_1, 8, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 9) {
                            row1.SENDATNAI = null;
                        } else {
                            row1.SENDATNAI = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 9);
                        }
                        if (colQtyInRs_tDBInput_1 < 10) {
                            row1.SENLIENAI = null;
                        } else {
                            row1.SENLIENAI = routines.system.JDBCUtil.getString(rs_tDBInput_1, 10, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 11) {
                            row1.SENDESPRO = null;
                        } else {
                            row1.SENDESPRO = routines.system.JDBCUtil.getString(rs_tDBInput_1, 11, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 12) {
                            row1.PCSCOD42_TO_TRANSLATE = null;
                        } else {
                            row1.PCSCOD42_TO_TRANSLATE = routines.system.JDBCUtil.getString(rs_tDBInput_1, 12, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 13) {
                            row1.CATPROCOD_TO_TRANSLATE = null;
                        } else {
                            row1.CATPROCOD_TO_TRANSLATE = routines.system.JDBCUtil.getString(rs_tDBInput_1, 13, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 14) {
                            row1.SENEMA = null;
                        } else {
                            row1.SENEMA = routines.system.JDBCUtil.getString(rs_tDBInput_1, 14, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 15) {
                            row1.SENNUMTELSEN = null;
                        } else {
                            row1.SENNUMTELSEN = routines.system.JDBCUtil.getString(rs_tDBInput_1, 15, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 16) {
                            row1.SENNOMUSECAP = null;
                        } else {
                            row1.SENNOMUSECAP = routines.system.JDBCUtil.getString(rs_tDBInput_1, 16, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 17) {
                            row1.SENDPTNUMNAI_TO_TRANSLATE = null;
                        } else {
                            row1.SENDPTNUMNAI_TO_TRANSLATE = routines.system.JDBCUtil.getString(rs_tDBInput_1, 17, false);
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
                         * [tFlowToIterate_1 main ] start
                         */

                        currentComponent = "tFlowToIterate_1";

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
                            "enc:routine.encryption.key.v1:C9mUwl0QjI0MUKE+2KR2E1SpHqPATJxaS1NirP4pSmT4ebQnhq3qCw=="
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

                        query_tDBRow_2 =
                            "Update senateurs.sen set sendespro='TOTO' where senmat='" + ((String) globalMap.get("matricule")) + "'";
                        whetherReject_tDBRow_2 = false;
                        globalMap.put("tDBRow_2_QUERY", query_tDBRow_2);
                        try {
                            stmt_tDBRow_2.execute(query_tDBRow_2);
                        } catch (java.lang.Exception e) {
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

                        if (
                            "com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) &&
                            routines.system.BundleUtils.inOSGi()
                        ) {
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
                         * [tFlowToIterate_1 process_data_end ] start
                         */

                        currentComponent = "tFlowToIterate_1";

                        /**
                         * [tFlowToIterate_1 process_data_end ] stop
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

                        if (
                            "com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_")) &&
                            routines.system.BundleUtils.inOSGi()
                        ) {
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
                 * [tFlowToIterate_1 end ] start
                 */

                currentComponent = "tFlowToIterate_1";

                globalMap.put("tFlowToIterate_1_NB_LINE", nb_line_tFlowToIterate_1);

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
            throw error;
        } finally {
            try {
                /**
                 * [tDBInput_1 finally ] start
                 */

                currentComponent = "tDBInput_1";

                /**
                 * [tDBInput_1 finally ] stop
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

            } catch (java.lang.Exception e) {
                // ignore
            } catch (java.lang.Error error) {
                // ignore
            }
            resourceMap = null;
        }

        globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
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
                Test_delete.class.getClassLoader().getResourceAsStream("irsen/test_delete_0_2/contexts/" + contextStr + ".properties");
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
            tDBInput_1Process(globalMap);
            if (!"failure".equals(status)) {
                status = "end";
            }
        } catch (TalendException e_tDBInput_1) {
            globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

            e_tDBInput_1.printStackTrace();
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
 * 51129 characters generated by Talend Open Studio for Data Integration on the
 * 17 janvier 2022 à 12:40:10 CET
 ************************************************************************************************/
