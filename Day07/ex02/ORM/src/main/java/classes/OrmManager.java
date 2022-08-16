package classes;

import models.OrmColumn;
import models.OrmColumnId;
import models.OrmEntity;
import org.reflections.Reflections;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrmManager {

    public static final String CONNECTION_ERROR = "Error: can't connection to DB";
    public static final String SERIAL_PRIMARY_KEY = " SERIAL PRIMARY KEY";
    public static final String CREATE_TABLE_IF_NOT_EXISTS_ = "CREATE TABLE IF NOT EXISTS ";
    public static final String SELECT_FROM = "SELECT * FROM ";
    public static final String WHERE_ID = " WHERE id=";
    public static final String ID = "id";
    public static final String STRING = "String";
    public static final String SET = " SET ";
    public static final String UPDATE_ = "UPDATE ";
    public static final String VALUES = ") VALUES (";
    public static final String INSERT_INTO_ = "INSERT INTO ";
    public static final String MODELS = "models";
    public static final String STRING1 = "STRING";
    public static final String VARCHAR = " varchar(";
    public static final String INTEGER = "INTEGER";
    public static final String LONG = "LONG";
    public static final String BIGINT = " BIGINT";
    public static final String DOUBLE = "DOUBLE";
    public static final String DOUBLE_PRECISION = " DOUBLE PRECISION";
    public static final String BOOLEAN = "BOOLEAN";
    public static final String BOOLEAN1 = " BOOLEAN";

    private Connection connection;

    public OrmManager(DataSource dataSource) {
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                System.err.println(CONNECTION_ERROR);
                System.exit(1);
            }
        } catch (SQLException e) {
            System.err.println(CONNECTION_ERROR);
            System.exit(1);
        }
        init();
    }

    private void init() {

        Reflections reflections = new Reflections(MODELS);
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(OrmEntity.class);

        List<String> classes = set.stream()
                .map(Class::getCanonicalName)
                .collect(Collectors.toList());

        for (String aClass : classes) {

            try {
                Class<?> clazz = Class.forName(aClass);

                OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);

                String dropper = "DROP TABLE IF EXISTS " + ormEntity.table() + " CASCADE";
                System.out.println(dropper);

                connection.createStatement().execute(dropper);

                Field[] fields = clazz.getDeclaredFields();

                StringBuilder sb = new StringBuilder(CREATE_TABLE_IF_NOT_EXISTS_);
                sb.append(ormEntity.table()).append(" (");

                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];

                    if (field.isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);

                        String type = field.getType().getSimpleName().toUpperCase();

                        sb.append(ormColumn.name());
                        switch (type) {
                            case STRING1:
                                sb.append(VARCHAR)
                                        .append(ormColumn.length())
                                        .append(")");
                                break;
                            case INTEGER:
                                sb.append(" ").append(type);
                                break;
                            case LONG:
                                sb.append(BIGINT);
                                break;
                            case DOUBLE:
                                sb.append(DOUBLE_PRECISION);
                                break;
                            case BOOLEAN:
                                sb.append(BOOLEAN1);
                                break;
                        }
                    }

                    if (field.isAnnotationPresent(OrmColumnId.class)) {
                        sb.append(field.getName()).append(SERIAL_PRIMARY_KEY);
                    }

                    if (i != fields.length - 1) {
                        sb.append(",\n");
                    }
                }

                sb.append(")");

                System.out.println(sb);

                connection.createStatement().execute(sb.toString());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(Object entity) {
        Class<?> clazz = entity.getClass();

        if (clazz.isAnnotationPresent(OrmEntity.class)) {
            OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);

            Field[] fields = clazz.getDeclaredFields();

            StringBuilder sb = new StringBuilder(INSERT_INTO_);
            sb.append(ormEntity.table()).append(" (");

            for (int i = 1; i < fields.length; i++) {
                Field field = fields[i];

                if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    sb.append(ormColumn.name());
                }

                if (i != fields.length - 1) {
                    sb.append(", ");
                }
            }

            sb.append(VALUES);

            for (int i = 1; i < fields.length; i++) {
                Field field = fields[i];

                extracted(entity, fields, sb, i, field);
            }

            sb.append(")");

            System.out.println(sb);

            try {
                connection.createStatement().execute(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Object entity) {
        Class<?> clazz = entity.getClass();

        if (clazz.isAnnotationPresent(OrmEntity.class)) {
            OrmEntity ormEntity = clazz.getAnnotation(OrmEntity.class);

            Field[] fields = clazz.getDeclaredFields();

            StringBuilder sb = new StringBuilder(UPDATE_);
            sb.append(ormEntity.table()).append(SET);

            Object id = null;

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];

                if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    sb.append(ormColumn.name());
                    sb.append("=");

                    extracted(entity, fields, sb, i, field);
                }

                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    field.setAccessible(true);
                    try {
                        id = field.get(entity);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    field.setAccessible(false);
                }

            }

            sb.append(WHERE_ID).append(id);

            System.out.println(sb);

            try {
                connection.createStatement().executeUpdate(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void extracted(Object entity, Field[] fields, StringBuilder sb, int i, Field field) {
        if (field.getType().getSimpleName().equalsIgnoreCase(STRING)) {
            sb.append("'");
        }

        field.setAccessible(true);
        try {
            sb.append(field.get(entity));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        field.setAccessible(false);

        if (field.getType().getSimpleName().equalsIgnoreCase(STRING)) {
            sb.append("'");
        }

        if (i != fields.length - 1) {
            sb.append(", ");
        }
    }

    public <T> T findById(Long id, Class<T> aClass){
        if (aClass.isAnnotationPresent(OrmEntity.class)) {
            try {
                OrmEntity entity = aClass.getAnnotation(OrmEntity.class);
                String sql_find_by_id = "SELECT * FROM " + entity.table() + " WHERE id = " + id + ";";
                PreparedStatement statement = connection.prepareStatement(sql_find_by_id);
                statement.execute();
                System.out.println(sql_find_by_id);
                ResultSet resultSet = statement.getResultSet();
                if(resultSet.next()) {
                    T object = aClass.newInstance();
                    for (Field f : aClass.getDeclaredFields()) {
                        f.setAccessible(true);
                        if (f.isAnnotationPresent(OrmColumnId.class)) {
                            f.set(object, resultSet.getLong("id"));
                        } else if (f.isAnnotationPresent(OrmColumn.class)) {
                            OrmColumn column = f.getAnnotation(OrmColumn.class);
                            f.set(object, resultSet.getObject(column.name()));
                        }
                    }
                    return object;
                }
                System.out.println("The record was not found!");
                return null;
            } catch (IllegalAccessException | SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                System.out.println("Not found constructor without parameters!");
                return null;
            }
        }
        System.out.println("The class is not associated with a database!");
        return null;
    }
}
