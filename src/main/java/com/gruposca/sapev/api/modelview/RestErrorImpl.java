/*******************************************************************************
 * Aedes Alert, Support to collect data to combat dengue
 * Copyright (C) 2017 Fundación Anesvad
 *   
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *   
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *   
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.gruposca.sapev.api.modelview;

public class RestErrorImpl implements RestError{
	private Integer mErrorCode;
	private String  mErrorDescription;
	private String  mErrorSource;
	
	@Override
	public  String getSource() { return mErrorSource; }
	private void   setSource(String source) { mErrorSource = source; }
	
	@Override
	public  Integer getCode() { return mErrorCode; }
    private void    setCode(Integer code) { mErrorCode = code; }
    
	@Override
	public  String getDescription() { return mErrorDescription; }
	private void   setDescription(String description) { mErrorDescription = description; }
	
	/**
	 * Constructor principal de la clase. 
	 * @param source Usa este parÃ¡metro para establecer el mÃ³dulo origen del error, se usarÃ¡ su nombre para incluirlo en la respuesta.
	 * @param code Usa este parÃ¡metro para establecer el cÃ³digo del error.
	 * @param description Usa este parametro para establecer la descripciÃ³n del error.
	 */	
	public RestErrorImpl(Class<?> source, Integer code, String description) {
		setCode(code);
		setDescription(description);
		setSource(source.getSimpleName());
	}	
	
	public static int    ERROR_CODE_AUTHORIZATION_FILTER                 = 2000;
	public static int    ERROR_CODE_AUTHORIZATION_FILTER_NOTFOUND        = 2001;
	
	public static String ERROR_DESC_AUTHORIZATION_FILTER                 = "Error en el filtro de authorizacion.";
	public static String ERROR_DESC_AUTHORIZATION_FILTER_NOTFOUND        = "Error en el filtro de authorizacion NOT FOUND";
	public static String ERROR_GENERAL_LOGIN                             = "Error de validación general.";
	public static String ERROR_LOGIN                                     = "Error de validación por login. Login o Password incorrectos.";
	public static String ERROR_GETTING_USER_BY_TOKEN                     = "Error obteniendo los datos del usuario.";
	public static String ERROR_GETTING_USER_BY_LOGIN                     = "Error obteniendo los datos del usuario mediante el nombre de usuario.";
	public static String ERROR_GETTING_USER_BY_EMAIL                     = "Error obteniendo los datos del usuario mediante el email.";

	public static String ERROR_GENERAL_USER_PERMISSION                   = "Error obteniedo permisos de usuario.";
	public static String ERROR_GETTING_USER_PERMISSION                   = "Error obteniendo perfil o modulo para la comprobación de permisos.";
	public static String ERROR_GET_HOUSE                                 = "Error obteniendo la casa.";
	public static String ERROR_GET_FATHER_ID                             = "Error no se ha recuperado correctamente el id del padre.";
	public static String ERROR_RESTRICCION_FK                            = "Excepcion controlada durante el borrado de la vivienda debido a una restriccion foreignKey de la base de datos.";
	public static String ERROR_GET_INSPECTION_ID                         = "Error obteniendo el id de la Inspeccion";
	public static String ERROR_GET_PLAN_ID                               = "Error obteniendo el plan";
	public static String ERROR_USEREXIST                                 = "Error, ya existe un usuario con el login indicado";
	public static String ERROR_GET_USER_ID                               = "Error id de usuario no encontrado.";
	public static String ERROR_GETTING_USERAREA_PERMISSION               = "Error comprobando los permisos del usuario para el área";
	public static String USER_MODULE_NOT_PERMISSION                      = "El usuario no tiene permisos para el servicio ";
	public static String USER_AREA_NOT_PERMISSION                        = "El usuario no tiene permisos para el area indicada ";
	public static String ERROR_GET_PROFILE_ID                            = "Error obteniendo el id del perfil";
	public static String ERROR_VALIDATE_RESTORE_PASSWORD                 = "Error validadndo y restaurando password usuario";
	public static String ERROR_SEND_URL_TO_RESTORE_PASSWORD              = "Error enviadndo el email con la url para recuperar contraseña de usuario. ";
	public static String ERROR_SEND_EMAIL_TO_INSPECTOR                   = "Error enviadndo el email con el resumen de la inspección. ";


	/* AccountWebService */
	public static String SERVICE_GET_ACCOUNT                             ="[SERVICE getUser()] : ";
	public static String SERVICE_UPDATE_ACCOUNT                          ="[SERVICE updateUser(AccountUpdateImpl account)] : ";

	/* AreaWebService */
	public static String SERVICE_GET_AREA                                ="[SERVICE getArea(@PathParam(id) Integer id)] : ";
	public static String SERVICE_CREATE_AREA                             ="[SERVICE createArea(AreaCreateUpdateImpl area)] : ";
	public static String SERVICE_UPDATE_AREA                             ="[SERVICE updateArea(@PathParam(id) Integer id, AreaCreateUpdateImpl area)] : ";
	public static String SERVICE_DELETE_AREA                             ="[SERVICE deleteUser(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_CHILDS                              ="[SERVICE getChilds(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_PARENTS                             ="[SERVICE getParents(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_HOUSES                              ="[SERVICE getHouses(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_LISTAREA                            ="[SERVICE getListArea()] : ";
	public static String SERVICE_GET_INSPECTIONS                         ="[SERVICE getInspections(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_REPORTS                             ="[SERVICE getReports(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_SECTORS                             ="[SERVICE getSectors(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_BLOCKS                              ="[SERVICE getBlocks(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_DESCENDANTS                         ="[SERVICE getDescendants(@PathParam(id) Integer id, @PathParam(typeId) Integer typeId)] : ";
	public static String SERVICE_GET_STATSMR                             ="[SERVICE getStatsMr(@PathParam('id')  Integer id, List<Integer> inspections)] : ";
	public static String SERVICE_GET_STATSEESS                           ="[SERVICE getStatsEess(@PathParam('id')  Integer id, List<Integer> inspections)] : ";
	public static String SERVICE_GET_STATS_HOUSESFOCUS                   ="[SERVICE getFocusHouses(@PathParam('id')  Integer id, List<Integer> inspections)] : ";


	/* ElementWebService */
	public static String SERVICE_GET_ELEMENT                             ="[SERVICE getElement(@PathParam(id) Integer id)] : ";
	public static String SERVICE_CREATE_ELEMENT                          ="[SERVICE createElement(ElementImpl element)] : ";
	public static String SERVICE_UPDATE_ELEMENT                          ="[SERVICE updateArea(@PathParam(id) Integer id, ElementImpl element)] : ";
	public static String SERVICE_DELETE_ELEMENT                          ="[SERVICE deleteUser(@PathParam(id) Integer id)] : ";
	public static String SERVICE_UP_ELEMENT                              ="[SERVICE upElement(@PathParam(id) Integer id)] : ";
	public static String SERVICE_DOWN_ELEMENT                            ="[SERVICE downElement(@PathParam(id) Integer id)] : ";

	/* HouseWebService */
	public static String SERVICE_GET_HOUSES_LIST                         ="[SERVICE getHousesList()] : ";
	public static String SERVICE_GET_INSPECTION                          ="[SERVICE getInspection(@PathParam(id)  Integer id)] : ";
	public static String SERVICE_CREATE_INSPECTION                       ="[SERVICE createInspections(InspectionImpl inspection)] : ";
	public static String SERVICE_UPDATE_HOUSE                            ="[SERVICE updateHouse(@PathParam(uuid) String uuid, HouseImpl house)] : ";
	public static String SERVICE_DELETE_HOUSE                            ="[SERVICE deleteHouse(@PathParam(uuid) String uuid)] : ";
	public static String SERVICE_GET_HOUSE_VISITS                        ="[SERVICE getVisitList(@PathParam(uuid) String uuid)] : ";
	public static String SERVICE_UPDATE_CODES                            ="[SERVICE updatecodesHouse()] : ";
	public static String SERVICE_UPDATE_ADDRESS                          ="[SERVICE updateAddress(@PathParam('uuid')  String uuid, HouseAreaImpl house)] : ";

	/* InspectionWebService */
	public static String SERVICE_GET_INSPECTION_LIST                     ="[SERVICE getInspectionsList()] : ";
	public static String SERVICE_GET_HOUSE                               ="[SERVICE getHouse(@PathParam(uuid) String uuid)] : ";
	public static String SERVICE_CREATE_HOUSE                            ="[SERVICE createHouse(HouseImpl house)] : ";
	public static String SERVICE_UPDATE_INSPECTION                       ="[SERVICE updateInspections(@PathParam(id) Integer id, InspectionImpl inspection)] : ";
	public static String SERVICE_DELETE_INSPECTION                       ="[SERVICE deleteInspection(@PathParam(id)  Integer id)] : ";
	public static String SERVICE_GET_INSPECTION_PERSONS                  ="[SERVICE getPersons(@PathParam(id)  Integer id)] : ";
	public static String SERVICE_GET_INSPECTION_VISITS                   ="[SERVICE getVisits (@PathParam(id)  Integer id)]: ";
	public static String SERVICE_POST_CLOSE_INSPECTION                   ="[SERVICE closeInspection (@PathParam(id)  Integer id)]: ";
	public static String SERVICE_GET_INSPECTION_BLOCKS                   ="[SERVICE getInspectionBlocks (@PathParam(id)  Integer id)]: ";
	public static String SERVICE_GET_SUBSTITUTES_EXCEL                   ="[SERVICE getReportSusbstitutes(@PathParam('id')  Integer id)]: ";
	public static String SERVICE_GET_INSPECTION_SAMPLES                  ="[SERVICE getSamples (@PathParam(id)  Integer id)]: ";
	public static String SERVICE_INSPECTION_SAMPLE_XLS                   ="[SERVICE getInspectionSampleXls(@PathParam('id')  Integer inspectionId) ] : ";
	
	
	/* LanguageWebService */
	public static String SERVICE_GET_LANGUAJE_LIST                       ="[SERVICE getLanguagesList()] : ";
		
	/* PlanWebService */
	public static String SERVICE_GET_PLAN                                ="[SERVICE getPlan(@PathParam(id) Integer id)] : ";
	public static String SERVICE_CREATE_PLAN                             ="[SERVICE createPlan(PlanImpl plan)] : ";
	public static String SERVICE_UPDATE_PLAN                             ="[SERVICE updatePlan(@PathParam(id) Integer id, PlanImpl plan)] : ";
	public static String SERVICE_DELETE_PLAN                             ="[SERVICE deletePlan(@PathParam(id) Integer id)] : ";
	public static String SERVICE_GET_PLAN_VISITS                         ="[SERVICE getVisitList(@PathParam(id) Integer id)] : ";
	public static String SERVICE_IMPORT_DFETAIL_PLAN                     ="[SERVICE importDetailPlan(List<HouseSyncImpl> listHouses] : ";
	public static String SERVICE_IMPORT_SUMMARY_PLAN                     ="[SERVICE importSummaryPlan(@PathParam(id) Integer id), PlanSummaryData planSummaryData] : ";
	public static String SERVICE_GET_PLAN_DETAIL                         ="[SERVICE getPlanDetail(@PathParam(id)  Integer id)] : ";
	public static String SERVICE_GET_LIST_PLAN                           ="[SERVICE getPlans()] : ";
	public static String SERVICE_SEND_INSPECTOR_REPORT                   ="[SERVICE sendInspectorReport(@PathParam(id)  Integer id)] : ";
	public static String SERVICE_GET_INSPECTOR_REPORT                    ="[SERVICE getInspectorReport(@PathParam(id)  Integer id)] : ";
	public static String SERVICE_CLOSE_PLAN                              ="[SERVICE closePlan(@PathParam(id)  Integer id)] : ";
	public static String SERVICE_CREATE_VISIT_PLAN                       ="[SERVICE createVisitPlan(@PathParam(id)  Integer id, PlanVisit planVisit)] : ";
	public static String SERVICE_SAVE_VISIT_PLAN                         ="[SERVICE saveVisitPlan(@PathParam(id)  Integer id, @PathParam(uuid)  String uuid,PlanVisit planVisit)] : ";
	public static String SERVICE_GETLIST_VISIT_PLAN                       ="[SERVICE getListVisitsPlan(@PathParam(id)  Integer id)] : ";
	
	
	/* ProfileWebService */
	public static String SERVICE_GET_PROFILE_LIST                        ="[SERVICE getProfileList()] : ";
	public static String SERVICE_GET_PROFILE                             ="[SERVICE getProfile (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_DELETE_PROFILE                          ="[SERVICE deleteProfile(@PathParam(id) Integer id)] : ";
	public static String SERVICE_CREATE_PROFILE                          ="[SERVICE createProfile(Profile profile)] : ";
	public static String SERVICE_UPDATE_PROFILE                          ="[SERVICE updateProfile(@PathParam(id) Integer id, Profile profile)] : ";

	/* SceneWebService */
	public static String SERVICE_GET_SCENES_LIST                         ="[SERVICE getScenesList()] : ";
	public static String SERVICE_GET_SCENE                               ="[SERVICE getScene(@PathParam(id) Integer id)] : ";
	public static String SERVICE_CREATE_SCENE                            ="[SERVICE createScene(SceneImpl scene)] : ";
	public static String SERVICE_UPDATE_SCENE                            ="[SERVICE updateScene(@PathParam(id) Integer id, SceneImpl scene)] : ";
	public static String SERVICE_DELETE_SCENE                            ="[SERVICE deleteScene(@PathParam(id) Integer id)] : ";

	/* SecurityWebService */
	public static String SERVICE_SECURITY_NOT_FOUND                      ="[SERVICE notFound()] : ";
	public static String SERVICE_AUTHORIZATE                             ="[SERVICE authorizate(LoginImpl credentials)] : ";
	public static String SERVICE_RESTORESPASSWORD                        ="[SERVICE restorePassword(RestorePass restorePass)] : ";
	public static String SERVICE_SEND_URL                                ="[SERVICE sendUrl(EmailToRestorePass emailToRestorePass)] : ";
	public static String SERVICE_VALIDATE_URL                            ="[SERVICE restorePassword(RestorePass restorePass)] : ";

	
	/* SyncWebService */
	public static String SERVICE_GET_SYNC_PLAN_LIST                      ="[SERVICE getPlanList()] : ";
	public static String SERVICE_GET_SYNC_PLAN                           ="[SERVICE getPlan (@PathParam(id)  Integer id)] : ";
	
	/* TableWebService */
	public static String SERVICE_GET_TABLE_LIST                          ="[SERVICE getTableList()] : ";
	public static String SERVICE_GET_TABLE                               ="[SERVICE getTable (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_CREATE_TABLE                            ="[SERVICE createTable (TableImpl table)] : ";
	public static String SERVICE_UPDATE_TABLE                            ="[SERVICE updateArea (@PathParam(id)  Integer id, TableImpl table)] : ";
	public static String SERVICE_DELETE_TABLE                            ="[SERVICE deleteTable(@PathParam(id)  Integer id) ] : ";
	public static String SERVICE_GET_TABLE_ELEMENTS                      ="[SERVICE getTableElements (@PathParam(id)  Integer id)] : ";

	/* TableWebService */
	public static String SERVICE_GET_USER_LIST                           ="[SERVICE getUserList()] : ";
	public static String SERVICE_GET_USER                                ="[SERVICE getUser (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_CREATE_USER                             ="[SERVICE createUser (UserCreateIml user)] : ";
	public static String SERVICE_UPDATE_USER                             ="[SERVICE updateUser (@PathParam(id)  Integer id, UserUpdateImpl user)] : ";
	public static String SERVICE_DELETE_USER                             ="[SERVICE deleteUser (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_UNLOCK_USER                             ="[SERVICE unlockUser (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_GET_USER_INSPECTION_LIST                ="[SERVICE getUserInspectionList(@PathParam(id)  Integer id)] : ";


	/* VisitWebService */
	public static String SERVICE_GET_VISIT_LIST                          ="[SERVICE getVisitList()] : ";
	public static String SERVICE_GET_VISIT                               ="[SERVICE getVisit (@PathParam(uuid)  String uuid)] : ";
	public static String SERVICE_GET_PHOTO                               ="[SERVICE getPhoto (@PathParam(uuid)  String uuid)] : ";
	public static String SERVICE_GET_INVENTORIES                         ="[SERVICE getInventories (@PathParam(uuid)  String uuid)] : ";
	public static String SERVICE_GET_SAMPLES                             ="[SERVICE getSamples(@PathParam(uuid)  String uuid)] : ";
	public static String SERVICE_GET_PERSONS                             ="[SERVICE getPersons (@PathParam(uuid)  String uuid)] : ";
	public static String SERVICE_DELETE_VISIT                            ="[SERVICE deleteVisit(@PathParam(\"uuid\")  String uuid)] : ";

	/* AlertsWebService */
	public static String ALERT_GET_ALERTS_LIST                           ="[SERVICE getAlertsList() ] : ";
	public static String ALERT_CLOSE_ALERT                               ="[SERVICE closeAlert(@PathParam(id)  Integer id)] : ";	
	
	/* SampleWebService */
	public static String SERVICE_UPLOAD_FILE                             ="[SERVICE uploadFile()] : ";
	public static String SERVICE_GET_SAMPLES_LIST                        ="[SERVICE getSamplesList()] : ";
	public static String SERVICE_SAMPLE_XLS                              ="[SERVICE getSampleXls(@PathParam(id)  Integer id) ] : ";

	/* ReportWebService */
	public static String SERVICE_GET_REPORTS_LIST                        ="[SERVICE getReportsList ()] : ";
	public static String SERVICE_GET_REPORT                              ="[SERVICE getElement(@PathParam(id) Integer id)] : ";
	public static String SERVICE_CREATE_REPORT                           ="[SERVICE createReport(ReportImpl report)] : ";
	public static String SERVICE_UPDATE_REPORT                           ="[SERVICE updateReport(@PathParam(id) Integer id, ReportImpl report)] : ";
	public static String SERVICE_DELETE_REPORT                           ="[SERVICE deleteReport(@PathParam(id) Integer id) ] : ";
	public static String SERVICE_SCHEDULE_REPORT                         ="[SERVICE getScheduleReport(ReportSchedule reportSchedule) ] : ";
	public static String SERVICE_PLAN_REPORT                             ="[SERVICE getPlanMapReport(@PathParam(id) Integer id) ] : ";
	public static String SERVICE_REPORT_XLSX                             ="[SERVICE getReportXls(@PathParam(id) Integer id) ] : ";
	public static String SERVICE_REPORT_PDF                              ="[SERVICE getReportPdf(@PathParam(id) Integer id) ] : ";
	public static String SERVICE_REPORT_SOURCES                          ="[SERVICE getReportSources(@PathParam(id) Integer id) ] : ";
	public static String SERVICE_REPORT_INSPECTIONS                      ="[SERVICE getInspections(ReportInspection reportInspection)  ] : ";

	
	/* MapsWebService */
	public static String SERVICE_GET_MAP_AEDIC                           ="[SERVICE getMapAedic(@PathParam(id) Integer id, MapDates mapDates) : ";
	public static String SERVICE_GET_MAP_FOCUS                           ="[SERVICE getMapFocus(@PathParam(id) Integer id, MapDates mapDates) : ";
	
	/* FebrileWebService */
	public static String SERVICE_GET_FEBRILE_LIST                        ="[SERVICE getFebrileList()] : ";
	public static String SERVICE_GET_FEBRILE                             ="[SERVICE getFebrile (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_CREATE_FEBRILE                          ="[SERVICE createFebrile (Febrile febrile)] : ";
	public static String SERVICE_UPDATE_FEBRILE                          ="[SERVICE updateFebrile (@PathParam(id)  Integer id, Febrile febrile)] : ";
	public static String SERVICE_DELETE_FEBRILE                          ="[SERVICE deleteFebrile (@PathParam(id)  Integer id)] : ";
	
	/* TrapWebService */
	public static String SERVICE_GET_TRAPS_LIST                          ="[SERVICE getList()] : ";
	public static String SERVICE_UPLOAD_TRAPS_FILE                       ="[SERVICE importFile()] : ";
	public static String SERVICE_GET_TRAP                                ="[SERVICE getTrap(@PathParam('id')  Integer id)] : ";
	public static String SERVICE_CREATE_TRAP                             ="[SERVICE createTrap(TrapModel trapModel)] : ";
	public static String SERVICE_UPDATE_TRAP                             ="[SERVICE updateTrap(@PathParam('id')  Integer id, TrapModel trapModel)] : ";
	public static String SERVICE_GET_TRAPSLOCATION_LIST                  ="[SERVICE getTrapLocationsList(@PathParam('id')  Integer id)] : ";
	public static String SERVICE_GET_TRAPLOCATION                        ="[SERVICE getTrapLocations(@PathParam('id')  Integer id) ] : ";
	public static String SERVICE_CREATE_TRAPLOCATION                     ="[SERVICE createTrapLocation(@PathParam('id')  Integer id, TrapLocationModel trapLocationModel)] : ";
	public static String SERVICE_UPDATE_TRAPLOCATION                     ="[SERVICE updateTrap(@PathParam('id')  Integer id, TrapLocationModel trapLocationModel)] : ";
	public static String SERVICE_GET_TRAPDATA_LIST                       ="[SERVICE getTrapDataList(FilterTrapData filterTrapData)] : ";
	public static String SERVICE_SAVE_TRAPDATA                           ="[SERVICE saveTrapData(TrapDataModel trapDataModel)] : ";
	public static String SERVICE_REPORT_TRAP                             ="[SERVICE reportTraps(FilterTrapExcel filterTrapExcel)] : ";
	

	/* ScheduleWebService */
	public static String SERVICE_GET_SCHEDULE_LIST                       ="[SERVICE getScheduleList()] : ";
	public static String SERVICE_DELETE_SCHEDULE                         ="[SERVICE deleteSchedule (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_GET_SCHEDULE                            ="[SERVICE getSchedule (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_CREATE_SCHEDULE                         ="[SERVICE createSchedule (Schedule schedule)] : ";
	public static String SERVICE_UPDATE_SCHEDULE                         ="[SERVICE updateSchedule (@PathParam(id)  Integer id, Schedule schedule)] : ";
	public static String SERVICE_GET_AREASCHILDS_SCHEDULE                ="[SERVICE getChilds(@PathParam('id')  Integer id)] : ";

	/* LarvicideWebService */
	public static String SERVICE_GET_LARVICIDE_LIST                      ="[SERVICE getLarvicideList()] : ";
	public static String SERVICE_GET_LARVICIDE                           ="[SERVICE getLarvicide (@PathParam(id)  Integer id)] : ";
	public static String SERVICE_CREATE_LARVICIDE                        ="[SERVICE createLarvicide (Larvicide larvicide)] : ";
	public static String SERVICE_UPDATE_LARVICIDE                        ="[SERVICE updateLarvicide (@PathParam(id)  Integer id, Larvicide larvicide)] : ";
	public static String SERVICE_DELETE_LARVICIDE                        ="[SERVICE deleteLarvicide(@PathParam(id)  Integer id) ] : ";
	
	
	/* Metodos de la clase AreaServiceImpl */
	public static String METHOD_GETAREA                                  ="[METHOD getArea (Integer id)] : ";
	public static String METHOD_CREATEAREA                               ="[METHOD createArea (AreaCreateUpdateImpl area)] : ";
	public static String METHOD_UPDATEAREA                               ="[METHOD updateArea (Integer id, AreaCreateUpdateImpl area)] : ";
	public static String METHOD_DELETEAREA                               ="[METHOD deleteArea (Integer id)] : ";
	public static String METHOD_GETCHILDS                                ="[METHOD getChilds (Integer id)] : ";
	public static String METHOD_GETLISTCHILDS                            ="[METHOD getListChilds (List<Areas> listAreas)] : ";
	public static String METHOD_GETPARENTS                               ="[METHOD getParents (Integer id)] : ";
	public static String METHOD_GETHOUSESAREA                            ="[METHOD getHousesArea (Integer id)] : ";
	public static String METHOD_GETLISTAREA                              ="[METHOD getListArea ()] : ";
	public static String METHOD_GETINSPECTIONSAREA                       ="[METHOD getInspectionsArea (Session session, Integer id)] : ";		
	public static String METHOD_GETUBIGEOCODE                            ="[METHOD getUbigeoCode (Integer id)] : ";
	public static String METHOD_GETREPORTSAREA                           ="[METHOD getReportsArea (Integer id)] : ";
	public static String METHOD_GETMAPAEDIC                              ="[METHOD getMapAedico(Integer id, MapDates mapDates)] : ";
	public static String METHOD_GETMAPFOCUS                              ="[METHOD getMapFocus(Session session, Integer id, MapDates mapDates)] : ";
	public static String METHOD_GETSECTORS                               ="[METHOD getSectors(Integer id)] : ";
	public static String METHOD_GETBLOCKS                                ="[METHOD getBlocks(Integer id)] : ";
	public static String METHOD_GETSCHEDULESAREA                         ="[METHOD getSchedulesArea(Integer id)] : ";
	public static String METHOD_GETTREEAREA                              ="[METHOD getTreeAreas()] : ";
	public static String METHOD_GETCHILDSTREE                            ="[METHOD getChilds(Areas area)] : ";
	public static String METHOD_GETLISTSTATSMR                           ="[METHOD getListStatsMr(Session session, Integer id, List<Integer> listInspections)] : ";
	public static String METHOD_GETLISTSTATSEESS                         ="[METHOD getListStatsEess(Session session, Integer id, List<Integer> listInspections)] : ";
	public static String METHOD_GETLISFOCUSHOUSES                        ="[METHOD getListFocusHouses(Session session, Integer id, List<Integer> listInspections)] : ";


	/* Metodos de la clase AccountServiceImpl */
	public static String METHOD_GETACCOUNT                               ="[METHOD getAccount (Session session)] : ";
	public static String METHOD_UPDATEACCOUNT                            ="[METHOD updateAccount (Session session, AccountUpdateImpl account)] : ";
	
	/* Metodos de la clase ElementServiceImpl */
	public static String METHOD_GETELEMENT                               ="[METHOD getElement (Session session, Integer id)] : ";
	public static String METHOD_CREATEELEMENT                            ="[METHOD createElement (Session session, ElementImpl element)] : ";
	public static String METHOD_UPDATEELEMENT                            ="[METHOD updateElement (Session session,Integer id, ElementImpl element)] : ";
	public static String METHOD_DELETEELEMENT                            ="[METHOD deleteElement (Session session, Integer id)] : ";
	public static String METHOD_REORDERSORTELEMENT                       ="[METHOD reorderSortElements (List<TableElements> listElements, TableElements elementDeleted)] : ";
	public static String METHOD_UPELEMENT                                ="[METHOD upElement (Integer id)] : ";
	public static String METHOD_DOWNELEMENT                              ="[METHOD downElement (Integer id)] : ";

	/* Metodos de la clase HouseServiceImpl */
	public static String METHOD_GETHOUSELIST                             ="[METHOD getHouseList()] : ";
	public static String METHOD_GETHOUSE                                 ="[METHOD getHouse (String uuid)] : ";
	public static String METHOD_CREATEHOUSE                              ="[METHOD createHouse (HouseImpl house)] : ";
	public static String METHOD_UPDATEHOUSE                              ="[METHOD updateHouse (String uuid, HouseImpl house)] : ";
	public static String METHOD_DELETEHOUSE                              ="[METHOD deleteHouse (String uuid)] : ";
	public static String METHOD_GETVISITHOUSELIST                        ="[METHOD getVisitsList (Session session, String uuid)] : ";
	public static String METHOD_SYNCHOUSES                               ="[METHOD syncHouses(Session session, List<HouseSyncImpl> listHouses)] : ";
	public static String METHOD_SYNCVISITS                               ="[METHOD syncVisit(Users user, Houses house, VisitSync visitSync)] : ";
	public static String METHOD_SYNCINVENTORIES                          ="[METHOD syncInventories(List<InventorySyncImpl> listInventories, Visits visit)] : ";
	public static String METHOD_SYNCPERSONS                              ="[METHOD syncPersons (List<PersonSyncImpl> listPersonSyncs, Houses house, Visits visit)] : ";
	public static String METHOD_SYNCSYMTOM                               ="[METHOD syncSymptom(List<SymptomSyncImpl> listSymptomSync, Persons person, Visits visit)] : ";
	public static String METHOD_SYNCSAMPLES                              ="[METHOD syncSamples(List<SampleSyncImpl> listSampleSyncImpl, Inventories inventory, Visits visit)] : ";
	public static String METHOD_UPDATE_CODES                             ="[METHOD updatecodesHouse()] : ";
	public static String METHOD_UPDATE_ADDRESS                           ="[METHOD updateAddresses(String uuid, HouseAreaImpl house)] : ";

	/* Metodos de la clase InspectionServiceImpl */
	public static String METHOD_GETLISTINSPECTION                        ="[METHOD getListInspection (Session session)] : ";
	public static String METHOD_GETINSPECTION                            ="[METHOD getInspection (Integer id)] : ";
	public static String METHOD_DELETEINSPECTION                         ="[METHOD deleteInspection (Integer id)] : ";
	public static String METHOD_CREATEINSPECTION                         ="[METHOD createInspection (InspectionImpl inspection)] : ";
	public static String METHOD_UPDATEINSPECTION                         ="[METHOD updateInspections (Integer id, InspectionImpl inspection)] : ";
	public static String METHOD_GETLISTPLANS                             ="[METHOD getListPlans (Integer inspectionId)] : ";
	public static String METHOD_GETLISTVISITSINSPECTION                  ="[METHOD getListVisit (Session session, Integer inspectionId) ] : ";
	public static String METHOD_CLOSEINSPECTION                          ="[METHOD closeInspection (Integer id)] : ";
	public static String METHOD_INSPECTION_GETBLOCKS                     ="[METHOD getBlocks(Integer id)] : ";
	public static String METHOD_SENDSPECTOREXCEL                         ="[METHOD sendInspectorExcel(Integer planId)] : ";
	public static String METHOD_CREATEREPORTINSPECTOR                    ="[METHOD createReportInspector(Integer planId)] : ";
	public static String METHOD_GETSUBSTITUTESREPORT                     ="[METHOD getSusbstitutesReport(Integer ID)] : ";
	public static String METHOD_GETSAMPLESINSPECTION                     ="[METHOD getListSamples(Integer inspectionId)] : ";

	
	/* Metodos de la clase LanguageServiceImpl */
	public static String METHOD_GETLANGUAJELIST                          ="[METHOD getLanguageList()] : ";	
	
	/* Metodos de la clase PlanServiceImpl */
	public static String METHOD_GETPLAN                                  ="[METHOD getPlan (Integer id)] : ";
	public static String METHOD_DELETEPLAN                               ="[METHOD deletePlan (Integer id)] : ";
	public static String METHOD_CREATEPLAN                               ="[METHOD createPlan (PlanImpl plan)] : ";
	public static String METHOD_UPDATEPLAN                               ="[METHOD updatePlan (Integer id, PlanImpl plan)] : ";
	public static String METHOD_GETLISTVISITS                            ="[METHOD getListVisits (Session session, Integer planId)] : ";
	public static String METHOD_IMPORT_SUMMARY                           ="[METHOD importSummaryPlan(Integer planId, PlanSummaryData planSummaryData)] : ";
	public static String METHOD_GET_PLANDETAIL                           ="[METHOD getPlanDetail(Integer id, Session session)] : ";
	public static String UPDATE_SYNC_FILE                                ="[METHOD updateSyncFilePlan(SyncData syncData)] : ";
	public static String CHECK_PLAN                                      ="[METHOD checkPlan(Integer planId))] : ";
	public static String GET_LIST_PENDING_PLANS                          ="[METHOD getListPendingPlans()] : ";
	public static String METHOD_INFOFROMVISITS                           ="[METHOD infoFromVisits()] : ";
	public static String METHOD_INFOFROMSUMMARIRES                       ="[METHOD infoFromSummaries()] : ";
	public static String METHOD_CREATE_CONSOLIDATE_INFO                  ="[METHOD createConsolidateInfo(Integer plan, Integer area, ClassPathXmlApplicationContext ctx)] : ";
	public static String METHOD_SAVE_VISIT_PLAN                          ="[METHOD saveVisitPlan(Integer planId, String uuid, PlanVisit planVisit)] : ";
	public static String METHOD_CREATE_VISIT_PLAN                        ="[METHOD createVisitPlan(Integer planId, PlanVisit planVisit)] : ";
	public static String METHOD_CLOSE_PLAN                               ="[METHOD closePlan(Integer planId)] : ";
	public static String METHOD_GET_LIST_VISIT_PLAN                      ="[METHOD getListVisitsPlan(Session session,Integer id)] : ";
	public static String METHOD_GET_LIST_PLAN_VISIT                      ="[METHOD getListPlanVisit(Integer planId, ParamFilterPlanVisits ParamFilterPlanVisits)] : ";

	
	/* Metodos de la clase ProfileServiceImpl */
	public static String METHOD_GETPROFILELIST                          ="[METHOD getProfileList()] : ";	
	public static String METHOD_GETPROFILE                              ="[METHOD getProfile(Integer id)] : ";	
	public static String METHOD_DELETEPROFILE                           ="[METHOD deleteProfile (Integer id)] : ";
	public static String METHOD_CREATEPROFILE                           ="[METHOD createProfile (Profiles profile)] : ";	
	public static String METHOD_UPDATEPROFILE                           ="[METHOD updateProfile (Integer id, Profiles profile)] : ";	
	
	
	/* Metodos de la clase SceneServiceImpl */
	public static String METHOD_GETSCENESLIST                           ="[METHOD getScenesList()] : ";	
	public static String METHOD_GETSCENE                                ="[METHOD getScene(Integer id)] : ";	
	public static String METHOD_CREATESCENE                             ="[METHOD createScene (SceneImpl scene)] : ";	
	public static String METHOD_UPDATESCENE                             ="[METHOD updateScene (Integer id, SceneImpl scene)] : ";	
	public static String METHOD_DELETESCENE                             ="[METHOD deleteScene (Integer id)] : ";	

	/* Metodos de la clase SyncServiceImpl */
	public static String METHOD_SYNC_GETPLANLIST                         ="[METHOD getPlansList (Session session)] : ";	
	public static String METHOD_SYNC_GETPLAN                             ="[METHOD getPlan (Integer id)] : ";	
	public static String METHOD_SYNC_GETAREALIST                         ="[METHOD getAreaList(List<Areas> entityListAreas)] : ";	
	public static String METHOD_SYNC_GETHOUSELIST                        ="[METHOD getHouseList (Integer areaId)] : ";	
	public static String METHOD_SYNC_GETPERSONLIST                       ="[METHOD getPersonList (UUID houseUuid)] : ";		
	
	/* Metodos de la clase TableServiceImpl */
	public static String METHOD_GETTABLELIST                             ="[METHOD getTableList (Session session)] : ";	
	public static String METHOD_GETTABLE                                 ="[METHOD getTable (Session session, Integer id)] : ";	
	public static String METHOD_CREATETABLE                              ="[METHOD createTable (TableImpl table)] : ";	
	public static String METHOD_UPDATETABLE                              ="[METHOD updateTable (Integer id, TableImpl table)] : ";	
	public static String METHOD_DELETETABLE                              ="[METHOD deleteTable (Integer id)] : ";	
	public static String METHOD_GETTABLEELEMENTLIST                      ="[METHOD getTableElementList (Session session, Integer id)] : ";	
	
	/* Metodos de la clase UserServiceImpl */
	public static String METHOD_GETUSER                                  ="[METHOD getUser (Integer id)] : ";	
	public static String METHOD_GETUSERLIST                              ="[METHOD getUserList()] : ";	
	public static String METHOD_GETUSERBYSESSION                         ="[METHOD getUserBySession (Session session)] : ";	
	public static String METHOD_SAVEUSER                                 ="[METHOD saveUser (UserCreateIml user)] : ";	
	public static String METHOD_UPDATEUSER                               ="[METHOD updateUser (Integer id, UserUpdateImpl user)] : ";	
	public static String METHOD_DELETEUSER                               ="[METHOD deleteUser (Integer id)] : ";	
	public static String METHOD_UNLOCKUSER                               ="[METHOD unlockUser (Integer id)] : ";	
	public static String METHOD_GETUSERINSPECTIONLIST                    ="[METHOD getUserInspectionList(Integer areaId)] : ";	

	
	/* Metodos de la clase VisitServiceImpl */
	public static String METHOD_GETVISITLIST                             ="[METHOD getVisitList (Session session)] : ";	
	public static String METHOD_GETVISIT                                 ="[METHOD getVisit (Session session,String uuid)] : ";	
	public static String METHOD_GETINVENTORIES                           ="[METHOD getInventories (Session session, String uuid)] : ";	
	public static String METHOD_GETSAMPLES                               ="[METHOD getSamples (Session session, String uuid)] : ";	
	public static String METHOD_GETPERSONS                               ="[METHOD getPersons (Session session, String uuid)] : ";	
	public static String METHOD_DELETEVISIT                              ="[METHOD deleteVisit(String uuid)] : ";	

	/* Metodos de la clase SampleServiceImpl */
	public static String METHOD_UPLOADFILE                               ="[METHOD uploadFile ()] : ";	
	public static String METHOD_CREATEXLSSAMPLES                         ="[METHOD String createXls(Integer inspectionId)] : ";	
	public static String METHOD_GETSAMPLELIST                            ="[METHOD List<Sample> getSampleList()] : ";	
	
	/* Metodos de la clase ReportServiceImpl */	
	public static String METHOD_GETREPORTSLIST                           ="[METHOD getReportList ()] : ";
	public static String METHOD_GETREPORT                                ="[METHOD getReport (Integer id)] : ";	
	public static String METHOD_CREATEREPORT                             ="[METHOD createReport (ReportImpl report)] : ";	
	public static String METHOD_UPDATEREPORT                             ="[METHOD updateReport (Integer id, ReportImpl report)] : ";	
	public static String METHOD_DELETEREPORT                             ="[METHOD deleteReport (Integer id)] : ";	

	/* Metodos de la clase ModuleServiceImpl */
	public static String METHOD_GETMODULELIST                            ="[METHOD getModuleList()] : ";		
	
	/* Metodos de la clase FebrileServiceImpl */
	public static String METHOD_GETFEBRILE                               ="[METHOD getFebrile (Integer id)] : ";
	public static String METHOD_DELETEFEBRILE                            ="[METHOD deleteFebrile (Integer id)] : ";
	public static String METHOD_CREATEFEBRILE                            ="[METHOD createFebrile (Febrile febrile)] : ";
	public static String METHOD_UPDATEFEBRILE                            ="[METHOD updateFebrile (Integer id, Febrile febrile)] : ";
	public static String METHOD_GETLISTFEBRILES                          ="[METHOD getListFebriles ()] : ";
	
	/* Metodos de la clase SampleServiceImpl */
	public static String METHOD_IMPORTFILE                               ="[METHOD importFile(File inputfile)] : ";	

	
	/* Metodos de la clase ScheduleServiceImpl */
	public static String METHOD_DELETESCHEDULE                           ="[METHOD deleteSchedule (Integer id)] : ";
	public static String METHOD_GETLISTSCHEDULE                          ="[METHOD getScheduleList(Session session, ParamFilterSchedule paramFilterSchedule)] : ";
	public static String METHOD_GETSCHEDULE                              ="[METHOD getSchedule (Integer id)] : ";
	public static String METHOD_CREATESCHEDULE                           ="[METHOD createSchedule (Schedule schedule)] : ";
	public static String METHOD_UPDATESCHEDULE                           ="[METHOD updateSchedule (Integer id, Schedule schedule)] : ";
	
	/* Metodos de la clase LarvicideServiceImpl */
	public static String METHOD_GETLARVICIDE                             ="[METHOD getLarvicide (Integer id)] : ";
	public static String METHOD_DELETELARVICIDE                          ="[METHOD deleteLarvicide (Integer id)] : ";
	public static String METHOD_CREATELARVICIDE                          ="[METHOD createLarvicide (Larvicide arvicide)] : ";
	public static String METHOD_UPDATELARVICIDE                          ="[METHOD updateLarvicide (Integer id, Larvicide arvicide)] : ";
	public static String METHOD_GETLISTLARVICIDE                         ="[METHOD getListLarvicide ()] : ";
	

	/* Metodos de la clase TrapsServiceImpl */
	public static String METHOD_GETLISTTRAPS                            ="[METHOD getListTraps(Session session, ParamFilterTraps paramFilterTraps)] : ";
	public static String METHOD_GETTRAP                                 ="[METHOD getTrap(Integer id)] : ";
	public static String METHOD_CREATETRAP                              ="[METHOD createTrap(TrapModel trapModel)] : ";
	public static String METHOD_UPDATETRAP                              ="[METHOD updateTrap(Integer id, TrapModel trapModel)] : ";
	public static String METHOD_GETLISTTRAPSLOCATIONS                   ="[METHOD getListTrapLocation(Integer trapId)] : ";
	public static String METHOD_GETTRAPSLOCATION                        ="[METHOD getTrapLocation(Integer id)] : ";
	public static String METHOD_CREATETRAPSLOCATION                     ="[METHOD createTrapLocations(TrapLocationModel trapLocationModel)] : ";
	public static String METHOD_UPDATETRAPSLOCATION                     ="[METHOD updateTrapLocations(Integer id, TrapLocationModel trapLocationModel)] : ";
	public static String METHOD_GETLISTTRAPDATA                         ="[METHOD getTrapDataList(FilterTrapData filterTrapData)] : ";
	public static String METHOD_SAVETRAPDATA                            ="[METHOD saveTrapData(TrapDataModel trapDataModel)] : ";
	public static String METHOD_REPORT_TRAP                             ="[METHOD reportTrap(FilterTrapExcel filterTrapExcel)] : ";
	
	public static String METHOD_UPDATEHOUSE_ERROR                        ="ERROR, uuid de la vivienda no encontrado";
	
}
