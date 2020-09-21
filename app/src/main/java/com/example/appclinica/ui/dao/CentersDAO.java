package com.example.appclinica.ui.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appclinica.ui.helpers.DBHelper;
import com.example.appclinica.ui.models.CenterModel;

import java.util.ArrayList;
import java.util.List;

public class CentersDAO {

    private DBHelper _dbHelper;

    public CentersDAO(Context c) {
        _dbHelper = new DBHelper(c);
    }

    //public void Insert(String idCenter, String nombre, String direccion, String distrito, double ubicacionLatitud, double ubicacionLongitud, String fotoUrl) throws DAOException {
    public void Insert(CenterModel centerModel) throws DAOException {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            //Object[] args = new Object[]{idCenter, nombre, direccion, distrito, ubicacionLatitud, ubicacionLongitud, fotoUrl};
            Object[] args = new Object[]{centerModel.getIdCenter(), centerModel.getName(), centerModel.getAddress(), centerModel.getCity(), centerModel.getLatitude(), centerModel.getLongitude(), centerModel.getPhotoUrl()};
            db.execSQL("INSERT INTO Sedes(IdSede, Nombre, Direccion, Distrito, UbicacionLatitud, UbicacionLongitud, FotoUrl) VALUES(?,?,?,?,?,?,?)", args);
        } catch (Exception e) {
            throw new DAOException("CentersDAO: Error al insertar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<CenterModel> GetAll() throws DAOException {
        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        List<CenterModel> centerModelList = new ArrayList<>();

        try {
            Cursor c = db.rawQuery("SELECT IdSede, Nombre, Direccion, Distrito, UbicacionLatitud, UbicacionLongitud, FotoUrl FROM Sedes", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    CenterModel centerModel = new CenterModel();
                    centerModel.setIdCenter(c.getInt(c.getColumnIndex("IdSede")));
                    centerModel.setName(c.getString(c.getColumnIndex("Nombre")));
                    centerModel.setAddress(c.getString(c.getColumnIndex("Direccion")));
                    centerModel.setCity(c.getString(c.getColumnIndex("Distrito")));
                    centerModel.setLatitude(c.getDouble(c.getColumnIndex("UbicacionLatitud")));
                    centerModel.setLongitude(c.getDouble(c.getColumnIndex("UbicacionLongitud")));
                    centerModel.setPhotoUrl(c.getString(c.getColumnIndex("FotoUrl")));
                    centerModelList.add(centerModel);
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            throw new DAOException("CentersDAO: Error al obtener: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return centerModelList;
    }

//    public ArrayList<CenterModel> GetById(String criterio) throws DAOException {
//        Log.i("CenterModelDAO", "buscar()");
//        SQLiteDatabase db = _dbHelper.getReadableDatabase();
//        ArrayList<CenterModel> lista = new ArrayList<CenterModel>();
//        try {
//            Cursor c = db.rawQuery("select id, titulo, descripcion from genero where titulo like '%"+criterio+"%' or descripcion like '%"+criterio+"%'", null);
//
//            if (c.getCount() > 0) {
//                c.moveToFirst();
//                do {
//                    int id = c.getInt(c.getColumnIndex("id"));
//                    String titulo = c.getString(c.getColumnIndex("titulo"));
//                    String descripcion = c.getString(c.getColumnIndex("descripcion"));
//
//                    CenterModel modelo = new CenterModel();
//                    modelo.setId(id);
//                    modelo.setTitulo(titulo);
//                    modelo.setDescripcion(descripcion);
//
//                    lista.add(modelo);
//                } while (c.moveToNext());
//            }
//            c.close();
//        } catch (Exception e) {
//            throw new DAOException("CenterModelDAO: Error al obtener: " + e.getMessage());
//        } finally {
//            if (db != null) {
//                db.close();
//            }
//        }
//        return lista;
//    }

    public void Delete(int idSede) throws DAOException {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        try {
            Integer[] args = new Integer[]{idSede};
            db.execSQL("DELETE FROM Sedes WHERE IdSede=?", args);
        } catch (Exception e) {
            throw new DAOException("CentersDAO: Error al eliminar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void DeleteAll() throws DAOException {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM Sedes");
        } catch (Exception e) {
            throw new DAOException("CentersDAO: Error al eliminar todos: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}



