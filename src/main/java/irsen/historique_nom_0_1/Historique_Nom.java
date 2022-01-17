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

package irsen.historique_nom_0_1;

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
 * Job: Historique_Nom Purpose: <br>
 * Description:  <br>
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class Historique_Nom implements TalendJob {

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
    private final String jobName = "Historique_Nom";
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
                    Historique_Nom.this.exception = e;
                }
            }
            if (!(e instanceof TalendException)) {
                try {
                    for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
                        if (m.getName().compareTo(currentComponent + "_error") == 0) {
                            m.invoke(Historique_Nom.this, new Object[] { e, currentComponent, globalMap });
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

    public void tDBSCD_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
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

    public static class SENOM_MAJStruct implements IPersistableRow<SENOM_MAJStruct> {

        static final byte[] commonByteArrayLock_IRSEN_Historique_Nom = new byte[0];
        static byte[] commonByteArray_IRSEN_Historique_Nom = new byte[0];

        public String senmat;

        public String getSenmat() {
            return this.senmat;
        }

        public String quacod;

        public String getQuacod() {
            return this.quacod;
        }

        public String sennomusecap;

        public String getSennomusecap() {
            return this.sennomusecap;
        }

        public String sennomtec;

        public String getSennomtec() {
            return this.sennomtec;
        }

        public String senprenomuse;

        public String getSenprenomuse() {
            return this.senprenomuse;
        }

        public String sennomuse;

        public String getSennomuse() {
            return this.sennomuse;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Historique_Nom.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Historique_Nom.length == 0) {
                        commonByteArray_IRSEN_Historique_Nom = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Historique_Nom = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Historique_Nom, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Historique_Nom, 0, length, utf8Charset);
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
                if (length > commonByteArray_IRSEN_Historique_Nom.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Historique_Nom.length == 0) {
                        commonByteArray_IRSEN_Historique_Nom = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Historique_Nom = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Historique_Nom, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Historique_Nom, 0, length, utf8Charset);
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
            synchronized (commonByteArrayLock_IRSEN_Historique_Nom) {
                try {
                    int length = 0;

                    this.senmat = readString(dis);

                    this.quacod = readString(dis);

                    this.sennomusecap = readString(dis);

                    this.sennomtec = readString(dis);

                    this.senprenomuse = readString(dis);

                    this.sennomuse = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Historique_Nom) {
                try {
                    int length = 0;

                    this.senmat = readString(dis);

                    this.quacod = readString(dis);

                    this.sennomusecap = readString(dis);

                    this.sennomtec = readString(dis);

                    this.senprenomuse = readString(dis);

                    this.sennomuse = readString(dis);
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

                writeString(this.sennomusecap, dos);

                // String

                writeString(this.sennomtec, dos);

                // String

                writeString(this.senprenomuse, dos);

                // String

                writeString(this.sennomuse, dos);
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

                writeString(this.sennomusecap, dos);

                // String

                writeString(this.sennomtec, dos);

                // String

                writeString(this.senprenomuse, dos);

                // String

                writeString(this.sennomuse, dos);
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
            sb.append(",sennomusecap=" + sennomusecap);
            sb.append(",sennomtec=" + sennomtec);
            sb.append(",senprenomuse=" + senprenomuse);
            sb.append(",sennomuse=" + sennomuse);
            sb.append("]");

            return sb.toString();
        }

        /**
         * Compare keys
         */
        public int compareTo(SENOM_MAJStruct other) {
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

        static final byte[] commonByteArrayLock_IRSEN_Historique_Nom = new byte[0];
        static byte[] commonByteArray_IRSEN_Historique_Nom = new byte[0];

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

        public String SENNOMUSECAP;

        public String getSENNOMUSECAP() {
            return this.SENNOMUSECAP;
        }

        public String SENNOMTEC;

        public String getSENNOMTEC() {
            return this.SENNOMTEC;
        }

        public String SENPRENOMUSE;

        public String getSENPRENOMUSE() {
            return this.SENPRENOMUSE;
        }

        private String readString(ObjectInputStream dis) throws IOException {
            String strReturn = null;
            int length = 0;
            length = dis.readInt();
            if (length == -1) {
                strReturn = null;
            } else {
                if (length > commonByteArray_IRSEN_Historique_Nom.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Historique_Nom.length == 0) {
                        commonByteArray_IRSEN_Historique_Nom = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Historique_Nom = new byte[2 * length];
                    }
                }
                dis.readFully(commonByteArray_IRSEN_Historique_Nom, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Historique_Nom, 0, length, utf8Charset);
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
                if (length > commonByteArray_IRSEN_Historique_Nom.length) {
                    if (length < 1024 && commonByteArray_IRSEN_Historique_Nom.length == 0) {
                        commonByteArray_IRSEN_Historique_Nom = new byte[1024];
                    } else {
                        commonByteArray_IRSEN_Historique_Nom = new byte[2 * length];
                    }
                }
                unmarshaller.readFully(commonByteArray_IRSEN_Historique_Nom, 0, length);
                strReturn = new String(commonByteArray_IRSEN_Historique_Nom, 0, length, utf8Charset);
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
            synchronized (commonByteArrayLock_IRSEN_Historique_Nom) {
                try {
                    int length = 0;

                    this.SENMAT = readString(dis);

                    this.QUACOD = readString(dis);

                    this.SENNOMUSE = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    this.SENNOMTEC = readString(dis);

                    this.SENPRENOMUSE = readString(dis);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void readData(org.jboss.marshalling.Unmarshaller dis) {
            synchronized (commonByteArrayLock_IRSEN_Historique_Nom) {
                try {
                    int length = 0;

                    this.SENMAT = readString(dis);

                    this.QUACOD = readString(dis);

                    this.SENNOMUSE = readString(dis);

                    this.SENNOMUSECAP = readString(dis);

                    this.SENNOMTEC = readString(dis);

                    this.SENPRENOMUSE = readString(dis);
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

                writeString(this.SENNOMUSECAP, dos);

                // String

                writeString(this.SENNOMTEC, dos);

                // String

                writeString(this.SENPRENOMUSE, dos);
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

                writeString(this.SENNOMUSECAP, dos);

                // String

                writeString(this.SENNOMTEC, dos);

                // String

                writeString(this.SENPRENOMUSE, dos);
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
            sb.append(",SENNOMUSECAP=" + SENNOMUSECAP);
            sb.append(",SENNOMTEC=" + SENNOMTEC);
            sb.append(",SENPRENOMUSE=" + SENPRENOMUSE);
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
                String currentMethodName = new Exception().getStackTrace()[0].getMethodName();
                resumeIt = resumeEntryMethodName.equals(currentMethodName);
            }
            if (resumeIt || globalResumeTicket) { //start the resume
                globalResumeTicket = true;

                row1Struct row1 = new row1Struct();
                SENOM_MAJStruct SENOM_MAJ = new SENOM_MAJStruct();

                /**
                 * [tDBSCD_1 begin ] start
                 */

                ok_Hash.put("tDBSCD_1", false);
                start_Hash.put("tDBSCD_1", System.currentTimeMillis());

                currentComponent = "tDBSCD_1";

                if (execStat) {
                    runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "SENOM_MAJ");
                }

                int tos_count_tDBSCD_1 = 0;

                class SCDSK_tDBSCD_1 {

                    private int hashCode;
                    public boolean hashCodeDirty = true;
                    String senmat;

                    public boolean equals(Object obj) {
                        if (this == obj) return true;
                        if (obj == null) return false;
                        if (getClass() != obj.getClass()) return false;
                        final SCDSK_tDBSCD_1 other = (SCDSK_tDBSCD_1) obj;
                        if (this.senmat == null) {
                            if (other.senmat != null) return false;
                        } else if (!this.senmat.equals(other.senmat)) return false;

                        return true;
                    }

                    public int hashCode() {
                        if (hashCodeDirty) {
                            int prime = 31;
                            hashCode = prime * hashCode + (senmat == null ? 0 : senmat.hashCode());
                            hashCodeDirty = false;
                        }
                        return hashCode;
                    }
                }

                class SCDStruct_tDBSCD_1 {

                    private String quacod;
                    private String sennomtec;
                    private String sennomusecap;
                    private String senprenomuse;
                    private String sennomuse;
                }

                int nb_line_update_tDBSCD_1 = 0;
                int nb_line_inserted_tDBSCD_1 = 0;
                int nb_line_rejected_tDBSCD_1 = 0;
                String dbSchema_tDBSCD_1 = "senateurs";
                Class.forName("org.postgresql.Driver");
                String connectionString_tDBSCD_1 = "jdbc:postgresql://" + "brandy-test.senat.fr" + ":" + "5603" + "/" + "sendevrep2";

                final String decryptedPassword_tDBSCD_1 = PasswordEncryptUtil.decryptPassword(
                    "enc:routine.encryption.key.v1:Dx/aUlJNu0IvdrF7UUb79KlthfFHUhnry2KcK9nXSWjirEknVjxoEw=="
                );

                java.sql.Connection connection_tDBSCD_1 = java.sql.DriverManager.getConnection(
                    connectionString_tDBSCD_1,
                    "pgdba",
                    decryptedPassword_tDBSCD_1
                );
                String tableName_tDBSCD_1 = null;
                if (dbSchema_tDBSCD_1 == null || dbSchema_tDBSCD_1.trim().length() == 0) {
                    tableName_tDBSCD_1 = "sennom";
                } else {
                    tableName_tDBSCD_1 = dbSchema_tDBSCD_1 + "\".\"" + "sennom";
                }
                java.sql.Timestamp timestamp_tDBSCD_1 = null;
                String tmpValue_tDBSCD_1 = null;
                String search_tDBSCD_1 =
                    "SELECT \"senmat\", \"quacod\", \"sennomtec\", \"sennomusecap\", \"senprenomuse\", \"sennomuse\" FROM \"" +
                    tableName_tDBSCD_1 +
                    "\" WHERE \"sennomdatfin\" IS NULL";
                java.sql.Statement statement_tDBSCD_1 = connection_tDBSCD_1.createStatement();
                java.sql.ResultSet resultSet_tDBSCD_1 = statement_tDBSCD_1.executeQuery(search_tDBSCD_1);
                java.util.Map<SCDSK_tDBSCD_1, SCDStruct_tDBSCD_1> cache_tDBSCD_1 = new java.util.HashMap<SCDSK_tDBSCD_1, SCDStruct_tDBSCD_1>();
                while (resultSet_tDBSCD_1.next()) {
                    SCDSK_tDBSCD_1 sk_tDBSCD_1 = new SCDSK_tDBSCD_1();
                    SCDStruct_tDBSCD_1 row_tDBSCD_1 = new SCDStruct_tDBSCD_1();
                    if (resultSet_tDBSCD_1.getObject(1) != null) {
                        sk_tDBSCD_1.senmat = resultSet_tDBSCD_1.getString(1);
                    }
                    if (resultSet_tDBSCD_1.getObject(2) != null) {
                        row_tDBSCD_1.quacod = resultSet_tDBSCD_1.getString(2);
                    }
                    if (resultSet_tDBSCD_1.getObject(3) != null) {
                        row_tDBSCD_1.sennomtec = resultSet_tDBSCD_1.getString(3);
                    }
                    if (resultSet_tDBSCD_1.getObject(4) != null) {
                        row_tDBSCD_1.sennomusecap = resultSet_tDBSCD_1.getString(4);
                    }
                    if (resultSet_tDBSCD_1.getObject(5) != null) {
                        row_tDBSCD_1.senprenomuse = resultSet_tDBSCD_1.getString(5);
                    }
                    if (resultSet_tDBSCD_1.getObject(6) != null) {
                        row_tDBSCD_1.sennomuse = resultSet_tDBSCD_1.getString(6);
                    }
                    cache_tDBSCD_1.put(sk_tDBSCD_1, row_tDBSCD_1);
                }
                resultSet_tDBSCD_1.close();
                statement_tDBSCD_1.close();
                String insertionSQL_tDBSCD_1 =
                    "INSERT INTO \"" +
                    tableName_tDBSCD_1 +
                    "\"(\"sennomid\", \"senmat\", \"quacod\", \"sennomtec\", \"sennomusecap\", \"senprenomuse\", \"sennomuse\", \"sennomdatdeb\", \"sennomdatfin\") VALUES(" +
                    "nextval('" +
                    ((dbSchema_tDBSCD_1 != null && dbSchema_tDBSCD_1.trim().length() != 0) ? dbSchema_tDBSCD_1 + "." : "") +
                    "seq_sennomid" +
                    "')" +
                    ", ?, ?, ?, ?, ?, ?, ?, ?)";
                java.sql.PreparedStatement insertionStatement_tDBSCD_1 = connection_tDBSCD_1.prepareStatement(insertionSQL_tDBSCD_1);
                insertionStatement_tDBSCD_1.setTimestamp(7, new java.sql.Timestamp(start_Hash.get("tDBSCD_1")));
                insertionStatement_tDBSCD_1.setNull(8, java.sql.Types.DATE);
                String updateSQLForType2_tDBSCD_1 =
                    "UPDATE \"" + tableName_tDBSCD_1 + "\" SET \"sennomdatfin\" = ? WHERE \"senmat\" = ? AND \"sennomdatfin\" IS NULL";
                java.sql.PreparedStatement updateForType2_tDBSCD_1 = connection_tDBSCD_1.prepareStatement(updateSQLForType2_tDBSCD_1);
                updateForType2_tDBSCD_1.setTimestamp(1, new java.sql.Timestamp(start_Hash.get("tDBSCD_1")));

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
                // ###############################

                // ###############################
                // # Vars initialization
                class Var__tMap_1__Struct {}
                Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
                // ###############################

                // ###############################
                // # Outputs initialization
                SENOM_MAJStruct SENOM_MAJ_tmp = new SENOM_MAJStruct();
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
                    "enc:routine.encryption.key.v1:h5vOPtmLPjBPDzzKtnU6UcLpAkpLeLbWtlNBZA=="
                );

                String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

                String url_tDBInput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "irsen";

                conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1, dbPwd_tDBInput_1);

                conn_tDBInput_1.setAutoCommit(false);

                java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

                String dbquery_tDBInput_1 = "Select \n	*\nfrom sennom";

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
                            row1.SENNOMUSECAP = null;
                        } else {
                            row1.SENNOMUSECAP = JDBCUtil.getString(rs_tDBInput_1, 4, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 5) {
                            row1.SENNOMTEC = null;
                        } else {
                            row1.SENNOMTEC = JDBCUtil.getString(rs_tDBInput_1, 5, false);
                        }
                        if (colQtyInRs_tDBInput_1 < 6) {
                            row1.SENPRENOMUSE = null;
                        } else {
                            row1.SENPRENOMUSE = JDBCUtil.getString(rs_tDBInput_1, 6, false);
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

                        // ###############################
                        { // start of Var scope
                            // ###############################
                            // # Vars tables

                            Var__tMap_1__Struct Var = Var__tMap_1; // ###############################
                            // ###############################
                            // # Output tables

                            SENOM_MAJ = null;

                            // # Output table : 'SENOM_MAJ'
                            SENOM_MAJ_tmp.senmat = row1.SENMAT;
                            SENOM_MAJ_tmp.quacod = row1.QUACOD;
                            SENOM_MAJ_tmp.sennomusecap = row1.SENNOMUSECAP;
                            SENOM_MAJ_tmp.sennomtec = row1.SENNOMTEC;
                            SENOM_MAJ_tmp.senprenomuse = row1.SENPRENOMUSE;
                            SENOM_MAJ_tmp.sennomuse = row1.SENNOMUSE;
                            SENOM_MAJ = SENOM_MAJ_tmp;
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
                        // Start of branch "SENOM_MAJ"
                        if (SENOM_MAJ != null) {
                            /**
                             * [tDBSCD_1 main ] start
                             */

                            currentComponent = "tDBSCD_1";

                            if (execStat) {
                                runStat.updateStatOnConnection(iterateId, 1, 1, "SENOM_MAJ");
                            }

                            try {
                                lookUpKey_tDBSCD_1 = new SCDSK_tDBSCD_1();
                                lookUpKey_tDBSCD_1.senmat = SENOM_MAJ.senmat;
                                lookUpKey_tDBSCD_1.hashCodeDirty = true;
                                lookUpValue_tDBSCD_1 = cache_tDBSCD_1.get(lookUpKey_tDBSCD_1);
                                if (lookUpValue_tDBSCD_1 == null) {
                                    lookUpValue_tDBSCD_1 = new SCDStruct_tDBSCD_1();

                                    if (SENOM_MAJ.senmat == null) {
                                        insertionStatement_tDBSCD_1.setNull(1, java.sql.Types.VARCHAR);
                                    } else {
                                        insertionStatement_tDBSCD_1.setString(1, SENOM_MAJ.senmat);
                                    }

                                    if (SENOM_MAJ.quacod == null) {
                                        insertionStatement_tDBSCD_1.setNull(2, java.sql.Types.VARCHAR);
                                    } else {
                                        insertionStatement_tDBSCD_1.setString(2, SENOM_MAJ.quacod);
                                    }

                                    if (SENOM_MAJ.sennomtec == null) {
                                        insertionStatement_tDBSCD_1.setNull(3, java.sql.Types.VARCHAR);
                                    } else {
                                        insertionStatement_tDBSCD_1.setString(3, SENOM_MAJ.sennomtec);
                                    }

                                    if (SENOM_MAJ.sennomusecap == null) {
                                        insertionStatement_tDBSCD_1.setNull(4, java.sql.Types.VARCHAR);
                                    } else {
                                        insertionStatement_tDBSCD_1.setString(4, SENOM_MAJ.sennomusecap);
                                    }

                                    if (SENOM_MAJ.senprenomuse == null) {
                                        insertionStatement_tDBSCD_1.setNull(5, java.sql.Types.VARCHAR);
                                    } else {
                                        insertionStatement_tDBSCD_1.setString(5, SENOM_MAJ.senprenomuse);
                                    }

                                    if (SENOM_MAJ.sennomuse == null) {
                                        insertionStatement_tDBSCD_1.setNull(6, java.sql.Types.VARCHAR);
                                    } else {
                                        insertionStatement_tDBSCD_1.setString(6, SENOM_MAJ.sennomuse);
                                    }

                                    nb_line_inserted_tDBSCD_1 += insertionStatement_tDBSCD_1.executeUpdate();
                                } else {
                                    if (
                                        (lookUpValue_tDBSCD_1.quacod == null && SENOM_MAJ.quacod != null) ||
                                        (lookUpValue_tDBSCD_1.quacod != null && !lookUpValue_tDBSCD_1.quacod.equals(SENOM_MAJ.quacod)) ||
                                        (lookUpValue_tDBSCD_1.sennomtec == null && SENOM_MAJ.sennomtec != null) ||
                                        (
                                            lookUpValue_tDBSCD_1.sennomtec != null &&
                                            !lookUpValue_tDBSCD_1.sennomtec.equals(SENOM_MAJ.sennomtec)
                                        ) ||
                                        (lookUpValue_tDBSCD_1.sennomusecap == null && SENOM_MAJ.sennomusecap != null) ||
                                        (
                                            lookUpValue_tDBSCD_1.sennomusecap != null &&
                                            !lookUpValue_tDBSCD_1.sennomusecap.equals(SENOM_MAJ.sennomusecap)
                                        ) ||
                                        (lookUpValue_tDBSCD_1.senprenomuse == null && SENOM_MAJ.senprenomuse != null) ||
                                        (
                                            lookUpValue_tDBSCD_1.senprenomuse != null &&
                                            !lookUpValue_tDBSCD_1.senprenomuse.equals(SENOM_MAJ.senprenomuse)
                                        ) ||
                                        (lookUpValue_tDBSCD_1.sennomuse == null && SENOM_MAJ.sennomuse != null) ||
                                        (
                                            lookUpValue_tDBSCD_1.sennomuse != null &&
                                            !lookUpValue_tDBSCD_1.sennomuse.equals(SENOM_MAJ.sennomuse)
                                        )
                                    ) {
                                        if (SENOM_MAJ.senmat == null) {
                                            updateForType2_tDBSCD_1.setNull(2, java.sql.Types.VARCHAR);
                                        } else {
                                            updateForType2_tDBSCD_1.setString(2, SENOM_MAJ.senmat);
                                        }

                                        nb_line_update_tDBSCD_1 += updateForType2_tDBSCD_1.executeUpdate();
                                        if (SENOM_MAJ.senmat == null) {
                                            insertionStatement_tDBSCD_1.setNull(1, java.sql.Types.VARCHAR);
                                        } else {
                                            insertionStatement_tDBSCD_1.setString(1, SENOM_MAJ.senmat);
                                        }

                                        if (SENOM_MAJ.quacod == null) {
                                            insertionStatement_tDBSCD_1.setNull(2, java.sql.Types.VARCHAR);
                                        } else {
                                            insertionStatement_tDBSCD_1.setString(2, SENOM_MAJ.quacod);
                                        }

                                        if (SENOM_MAJ.sennomtec == null) {
                                            insertionStatement_tDBSCD_1.setNull(3, java.sql.Types.VARCHAR);
                                        } else {
                                            insertionStatement_tDBSCD_1.setString(3, SENOM_MAJ.sennomtec);
                                        }

                                        if (SENOM_MAJ.sennomusecap == null) {
                                            insertionStatement_tDBSCD_1.setNull(4, java.sql.Types.VARCHAR);
                                        } else {
                                            insertionStatement_tDBSCD_1.setString(4, SENOM_MAJ.sennomusecap);
                                        }

                                        if (SENOM_MAJ.senprenomuse == null) {
                                            insertionStatement_tDBSCD_1.setNull(5, java.sql.Types.VARCHAR);
                                        } else {
                                            insertionStatement_tDBSCD_1.setString(5, SENOM_MAJ.senprenomuse);
                                        }

                                        if (SENOM_MAJ.sennomuse == null) {
                                            insertionStatement_tDBSCD_1.setNull(6, java.sql.Types.VARCHAR);
                                        } else {
                                            insertionStatement_tDBSCD_1.setString(6, SENOM_MAJ.sennomuse);
                                        }

                                        nb_line_inserted_tDBSCD_1 += insertionStatement_tDBSCD_1.executeUpdate();
                                    }
                                }
                            } catch (Exception e) { //catch
                                globalMap.put("tDBSCD_1_ERROR_MESSAGE", e.getMessage());

                                System.err.print(e.getMessage());
                            } //end catch

                            lookUpValue_tDBSCD_1.quacod = SENOM_MAJ.quacod;
                            lookUpValue_tDBSCD_1.sennomtec = SENOM_MAJ.sennomtec;
                            lookUpValue_tDBSCD_1.sennomusecap = SENOM_MAJ.sennomusecap;
                            lookUpValue_tDBSCD_1.senprenomuse = SENOM_MAJ.senprenomuse;
                            lookUpValue_tDBSCD_1.sennomuse = SENOM_MAJ.sennomuse;
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

                        } // End of branch "SENOM_MAJ"

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
                 * [tDBSCD_1 end ] start
                 */

                currentComponent = "tDBSCD_1";

                insertionStatement_tDBSCD_1.close();
                updateForType2_tDBSCD_1.close();
                if (connection_tDBSCD_1 != null && !connection_tDBSCD_1.isClosed()) {
                    connection_tDBSCD_1.close();
                }
                globalMap.put("tDBSCD_1_NB_LINE_UPDATED", nb_line_update_tDBSCD_1);
                globalMap.put("tDBSCD_1_NB_LINE_INSERTED", nb_line_inserted_tDBSCD_1);
                globalMap.put("tDBSCD_1_NB_LINE_REJECTED", nb_line_rejected_tDBSCD_1);

                if (execStat) {
                    runStat.updateStat(resourceMap, iterateId, 2, 0, "SENOM_MAJ");
                }

                ok_Hash.put("tDBSCD_1", true);
                end_Hash.put("tDBSCD_1", System.currentTimeMillis());
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
        final Historique_Nom Historique_NomClass = new Historique_Nom();

        int exitCode = Historique_NomClass.runJobInTOS(args);

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
                Historique_Nom.class.getClassLoader()
                    .getResourceAsStream("irsen/historique_nom_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = Historique_Nom.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
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
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : Historique_Nom");
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
 *     59139 characters generated by Talend Open Studio for Data Integration
 *     on the 13 janvier 2022 à 15:30:37 CET
 ************************************************************************************************/
