package com.scb.fmsw.mobile;

public interface WorkflowConstants {

    //Note: public static final is redundant for interface variables

    //Tests Group
    String TEST_GRP_ACKNOWLEDGE = "ACKNOWLEDGE";
    String TEST_GRP_ACKNOWLEDGE_SELECTED = "ACKNOWLEDGE SELECTED";
    String TEST_GRP_ACKNOWLEDGE_ALL = "ACKNOWLEDGE ALL";
    String TEST_GRP_CLARIFICATION = "REQUEST FOR CLARIFICATION";
    String TEST_GRP_CLARIFICATION_SELECTED = "REQUEST FOR CLARIFICATION SELECTED";
    String TEST_GRP_CLARIFICATION_ALL = "REQUEST FOR CLARIFICATION ALL";
    String TEST_GRP_RESPOND = "RESPOND";
    String TEST_GRP_RESPOND_SELECTED = "RESPOND SELECTED";
    String TEST_GRP_REASSIGN = "REASSIGN";
    String TEST_GRP_REASSIGN_SELECTED = "REASSIGN SELECTED";
    String TEST_GPR_REASSIGN_ALL = "REASSIGN ALL";
    String TEST_GRP_EXPLICIT_DELEGATION = "EXPLICIT DELEGATION";
    String TEST_GRP_EXPLICIT_DELEGATION_SELECTED = "EXPLICIT DELEGATION SELECTED";
    String TEST_GRP_EXPLICIT_DELEGATION_ALL = "EXPLICIT DELEGATION ALL";
    String TEST_GRP_BOOKMARK = "BOOKMARK";
    String TEST_GRP_BOOKMARK_SELECTED = "BOOKMARK SELECTED";
    String TEST_GRP_BOOKMARK_ALL = "BOOKMARK ALL";
    String TEST_GRP_DEFAULT_DELEGATION = "DEFAULT DELEGATION";
    String TEST_GRP_USERS_DELEGATION = "USERS DELEGATION";
    String TEST_GRP_HIERARCHY_DELEGATION = "HIERARCHY DELEGATION";
    String TEST_GRP_CNA = "CNA";
    String TEST_GRP_IPV_FVA = "IPV/FVA";
    String TEST_GRP_PNL = "PNL";
    String TEST_GRP_GT_GMR = "GT/GMR";
    String TEST_GRP_OMR = "OMR";
    String TEST_GRP_CE = "CE";
    String TEST_GRP_VE = "VE";
    String TEST_GRP_TM = "TM";
    String TEST_GRP_MULTIPLE_WORKFLOW = "MULTIPLE WORKFLOW";
    String TEST_GRP_CLARIFICATION_OTHERS = "OTHERS";
    String TEST_GRP_CLARIFICATION_PERFORMER = "PERFORMER";
    String TEST_GRP_CLARIFICATION_LM = "LM";
    String TEST_GRP_CLARIFICATION_MO = "MO";
    String TEST_GRP_CLARIFICATION_PC = "PC";
    String TEST_GRP_CLARIFICATION_VC = "VC";
    String TEST_GRP_CLARIFICATION_MRO_OR_MTCR_OR_GT = "MRO/MTCR/GT";
    String TEST_GRP_CLARIFICATION_MTCR_OR_MRO_UPLOADER = "MTRC/MRO UPLOADER";
    String TEST_GRP_CLARIFICATION_VDO = "VDO";
    String TEST_GRP_ACCEPT_DELEGATION = "ACCEPT DELEGATION";
    String TEST_GRP_REJECT_DELEGATION = "REJECT DELEGATION";

    //Navigation Bar
    String NAV_BAR_TITLE_TO_LIMIT_MONITORING = "Request for Clarification - To Limit Monitoring";
    String NAV_BAR_TITLE_TO_MTCR = "Request for Clarification - To MTCR";


    //----------------------------- Workflow Status ----------------------------

    //CE Workflow
    String WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING = "Pending Clarification with Limit Monitoring";
    String WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_MTCR = "Pending Clarification with MTCR";
    String WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION = "Pending MTCR Review Post clarification";
    String WORKFLOW_STATUS_PENDING_FO_REVIEW_POST_CLARIFICATION = "Pending FO Review Post clarification";
    String WORKFLOW_STATUS_PENDING_FO_REVIEW = "Pending FO Review";
    String WORKFLOW_STATUS_REVIEWED_AND_DISCIPLINARY_ACTION_TAKEN = "Reviewed & Disciplinary action taken";

    //DATA Workflow
    String WORKFLOW_STATUS_ACKNOWLEDGED = "Acknowledged";
    String WORKFLOW_STATUS_PENDING_CLARIFICATION = "Pending Clarification";
    String WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION = "Pending Ack-Post Clarification";

    //PNL Workflow
    String WORKFLOW_STATUS_PENDING_REVIEW_AND_ACCEPTANCE_POST_CLARIFICATION = "Pending Review & Acceptance Post Clarification";
    String WORKFLOW_STATUS_REVIEWED_AND_ACCEPTED = "Reviewed & Accepted";

    //VE Workflow
    String WORKFLOW_STATUS_PENDING_VDO_CLARIFICATION = "Pending VDO Clarification";
    String WORKFLOW_STATUS_PENDING_MTCR_OR_MRO_UPLOADER_CLARIFICATION = "Pending MTCR/MRO Uploader Clarification";
    String WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION = "Pending Compliance Action Post Clarification";
    String WORKFLOW_STATUS_PENDING_COMPLIANCE_CLARIFICATION_UT = "Pending Compliance Clarification - UT";
    String WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_UT = "Pending Compliance Action - UT";
    String WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION_UT = "Pending Compliance Action Post Clarification - UT";

    //GMR Workflow
    String WORKFLOW_STATUS_APPROVED = "Approved";
    String WORKFLOW_STATUS_PENDING_APPROVAL_POST_CLARIFICATION = "Pending Approval Post Clarification";

    //TM Workflow
    String WORKFLOW_STATUS_PENDING_TRADER_APPROVAL = "Pending Trader Approval";
    String WORKFLOW_STATUS_PENDING_FM_HEAD_APPROVAL = "Pending FM Head Approval";
    String WORKFLOW_STATUS_PENDING_SUPERVISOR_APPROVAL = "Pending Supervisor Approval";

    //-------------------------------------------------------------------------

    //Alert Title
    String ALERT_TITLE_SUCCESS = "Message";
    String ALERT_TITLE_FAILED = "Alert!";

    //Alert Message
    String ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED = "The workflow status has been updated, you can no longer perform any actions on the workflow";
    String ALERT_MSG_SELECT_LATE_CODE = "Please select value of Late Code";
    String ALERT_MSG_SELECT_LATE_RESPONSE_CODE = "Please select value of Late Response Code";
    String ALERT_MSG_UNEXPECTED_ERROR_OCCURRED = "Unexpected error occured";
    String ALERT_MSG_STAFF_NOT_AVAILABLE = "Selected staff is not available in the system or does not have a valid role.Please select another option";
    String ALERT_MSG_WORKFLOW_CANNOT_SELF_DELEGATE = "Workflow cannot be self delegated";
    String ALERT_MSG_DELEGATION_CANNOT_SELF_DELEGATE = "Cannot delegate to Same Person";
    String ALERT_MSG_SUCCESSFULLY_CREATED_DELEGATION = "You have successfully created delegation to";
    String ALERT_MSG_ALREADY_HAS_DEFAULT_DELEGATION = "already has a default delegation for the selected priority or user";
    String ALERT_MSG_EXISTING_ACTIVE_DELEGATION = "You already have an existing active delegation in the period/date selected so you cannot create another delegation";
    String ALERT_MSG_ACCEPT_DELEGATION = "You are about to accept all the selected pending delegations. Do you wish to continue? Only pending delegations will be accepted.";
    String ALERT_MSG_REJECT_DELEGATION = "You are about to reject all the selected pending delegations. Do you wish to continue? Only pending delegations will be rejected.";
    String ALERT_MSG_DELETE_DELEGATION = "You are about to delete delegation. Do you wish to continue?";
    String ALERT_MSG_CAN_ONLY_ACCEPT_PENDING_DELEGATION = "None of the selected delegations are pending so no action has been taken.";
    String ALERT_MSG_ACCEPTED_SUCCESSFULLY = "1 delegation accepted successfully";
    String ALERT_MSG_REJECTED_SUCCESSFULLY = "1 delegation rejected successfully";
    String ALERT_MSG_DELETED_SUCCESSFULLY = "1 delegation deleted successfully";
    String ALERT_MSG_NONE_OF_THE_MSG_ARE_MATCHED = "None of Alert Message are matched";
    String ALERT_MSG_NETWORK_CONNECTION_WAS_LOST = "The network connection was lost.";

    //Message
    String MSG_NO_MORE_LOAD_RESULTS_BUTTON = "There is no more Load More Results Button";
    String MSG_VERIFIED_CLARIFICATION_OPTIONS = "Verified Clarification Options";
    String MSG_ENTER_COMMENT = "Mobile Automation Testing, Comment";
    String MSG_ENTER_LATE_COMMENT = "Mobile Automation Testing, Late Comment";
    String MSG_ENTER_ISSUE_FLAG_COMMENT = "Mobile Automation Testing, Issue Flag Comment";
    String MSG_ENTER_RISK_ASSESSMENT_COMMENT = "Mobile Automation Testing, Risk Assessment Comment";
    String MSG_ENTER_EXPLANATION_COMMENT = "Mobile Automation Testing, Explanation Comment";
    String MSG_ENTER_CONTROL_BREAKDOWN_COMMENT = "Mobile Automation Testing, Control Breakdown Comment";
    String MSG_ENTER_OUTCOME_COMMENT = "Mobile Automation Testing, Outcome Comment";
    String MSG_ENTER_GROUP_REMARK = "Mobile Automation Testing, Group Remark";
    String MSG_ENTER_FO_JUSTIFICATION_COMMENT = "Mobile Automation Testing, Front Office Justification Comment";
    String MSG_ENTER_PREVENT_RECURRENCE_COMMENT = "Mobile Automation Testing, Prevent Recurrence Comment";
    String MSG_ENTER_SUPERVISOR_REMARK = "Mobile Automation Testing, Supervisor Remark";
    String MSG_ENTER_REMEDIATION_ACTION_COMMENT = "Mobile Automation Testing, Remediation Action Comment";
    String MSG_ENTER_VDO_COMMENT = "Mobile Automation Testing, VDO Comment";
    String MSG_ENTER_FO_JUSTIFICATION_COMMENT_EDIT = "Mobile Automation Testing, Front Office Justification Comment Edited";
    String MSG_ENTER_PREVENT_RECURRENCE_COMMENT_EDIT = "Mobile Automation Testing, Prevent Recurrence Comment Edited";
    String MSG_ENTER_VDO_COMMENT_EDIT = "Mobile Automation Testing, VDO Comment Edited";
    String MSG_ENTER_REMEDIATION_ACTION_COMMENT_EDIT = "Mobile Automation Testing, Remediation Action Comment Edited";

    //Success Message
    String SUCCESS_MSG_SUCCESSFULLY_ACKNOWLEDGE_WORKFLOW = "Successfully Acknowledge Workflow";
    String SUCCESS_MSG_SUCCESSFULLY_SENT_WORKFLOW_FOR_CLARIFICATION = "Successfully sent Workflow for Clarification";
    String SUCCESS_MSG_SUCCESSFULLY_REASSIGN_WORKFLOW = "Successfully Reassign Workflow";
    String SUCCESS_MSG_SUCCESSFULLY_SUBMIT_WORKFLOW = "Successfully Submit Workflow";
    String SUCCESS_MSG_SUCCESSFULLY_DELEGATE_WORKFLOW = "Successfully Delegate Workflow";
    String SUCCESS_MSG_SUCCESSFULLY_BOOKMARK_WORKFLOW = "Successfully Bookmark Workflow";
    String SUCCESS_MSG_SUCCESSFULLY_CREATE_DEFAULT_DELEGATION = "Successfully created Default Delegation";
    String SUCCESS_MSG_SUCCESSFULLY_CREATE_USERS_DELEGATION = "Successfully created Users Delegation";

    //Failed Message
    String FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW = "Failed to Acknowledge $1 Workflow";
    String FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW = "Failed to Acknowledge Selected workflow";
    String FAILED_MSG_FAILED_TO_ACKNOWLEDGE_ALL_WORKFLOW = "Failed to Acknowledge All workflow";
    String FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION = "Failed to sent $1 Workflow for Clarification";
    String FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION = "Failed to sent Selected Workflow for Clarification";
    String FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION = "Failed to sent All Workflow for Clarification";
    String FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW = "Failed to Reassign $1 Workflow";
    String FAILED_MSG_FAILED_TO_REASSIGN_SELECTED_WORKFLOW = "Failed to Reassign Selected Workflow";
    String FAILED_MSG_FAILED_TO_REASSIGN_ALL_WORKFLOW = "Failed to Reassign All Workflow";
    String FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW = "Failed to Submit $1 Workflow";
    String FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW = "Failed to Submit Selected Workflow";
    String FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW = "Failed to Submit All Workflow";
    String FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW = "Failed to Delegate $1 Workflow";
    String FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW = "Failed to Delegate Selected Workflow";
    String FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW = "Failed to Delegate All Workflow";
    String FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW = "Failed to Bookmark $1 Workflow";
    String FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW = "Failed to Bookmark Selected Workflow";
    String FAILED_MSG_FAILED_TO_BOOKMARK_ALL_WORKFLOW = "Failed to Bookmark All Workflow";
    String FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION = "Failed to create Default Delegation";
    String FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION = "Failed to create Users Delegation";
    String FAILED_MSG_FAILED_TO_REVIEW_AND_ASSESS_WORKFLOW = "Failed to Review and Assess Workflow";
    String FAILED_MSG_FAILED_TO_REVIEW_AND_APPROVE_WORKFLOW = "Failed to Review and Approve Workflow";
    String FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW = "Failed to Approve Workflow";
    String FAILED_MSG_FAILED_TO_REVIEW_AND_ACTION_WORKFLOW = "Failed to Review and Action Workflow";
    String FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS = "Failed to match Comments/Workflow Status";

    //Error Message
    String ERROR_MSG_NO_USER_FOUND = "There is no user found with the given PSID: $1";
    String ERROR_MSG_NO_GROUP_FOUND = "There is no group found with the given Group Name: $1";
    String ERROR_MSG_NO_RESULT_FOUND = "There is no result found with the given keyword: $1";
    String ERROR_MSG_TABLE_CONTAINER_NOT_LOADED = "Table Container has not loaded";
    String ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED = "Button Container has not loaded";
    String ERROR_MSG_TAB_CONTAINER_NOT_LOADED = "Tab Container has not loaded";
    String ERROR_MSG_CONTAINER_NOT_LOADED = "Container has not loaded";
    String ERROR_MSG_NO_WORKFLOW_FOUND = "There is no workflow found";
    String ERROR_MSG_NO_WORKFLOW_FOUND_WITH_THAT_WORKFLOW_ID = "There is no workflow found with that workflow id";
    String ERROR_MSG_NOT_ENOUGH_WORKFLOW = "There are not enough workflow or there is no workflow";
    String ERROR_MSG_UNABLE_TO_FIND_BOOKMARK_ELEMENT = "Unable to find Bookmark element";
    String ERROR_MSG_UNABLE_TO_FIND_PREV_ACTOR_TYPE_ELEMENT = "Unable to find Prev Actor Type element";
    String ERROR_MSG_UNABLE_TO_FIND_CURR_ACTOR_ELEMENT = "Unable to find Curr Actor element";
    String ERROR_MSG_UNABLE_TO_FIND_COMMENT_ELEMENT = "Unable to find $1 element";
    String ERROR_MSG_UNABLE_TO_FIND_WORKFLOW_STATUS_ELEMENT = "Unable to find Workflow Status element";
    String ERROR_MSG_UNABLE_TO_FIND_WORKFLOW_EVENT_STATUS_ELEMENT = "Unable to find Workflow Event Status element";
    String ERROR_MSG_UNABLE_TO_FIND_CURR_ACTOR_TYPE_ELEMENT = "Unable to find Curr Actor Type element";
    String ERROR_MSG_UNABLE_TO_FIND_CURR_ACTOR_GROUP_ELEMENT = "Unable to find Curr Actor Group element";
    String ERROR_MSG_APPIUM_DRIVER_NOT_FOUND = "Appium driver could not be initialised for device";
    String ERROR_MSG_NO_ELEMENT_FOUND = "$1 Element could not be found";
    String ERROR_MSG_DO_NOT_HAVE_SWITCH_ELEMENT = "This clarification option does not have this switch element";
    String ERROR_MSG_NO_WORKFLOW_TYPE = "There is no Workflow Type";
    String ERROR_MSG_NO_COUNTRY = "There is no country";
    String ERROR_MSG_DELEGATION_NOT_MATCHED = "No matched delegation found";
    String ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW = "Failed to verify workflow";
    String ERROR_MSG_CLARIFICATION_OPTION_NOT_MATCHED = "None of the Clarification option are matched";
    String ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION = "Failed to verify Clarification Options";

    //Screenshot Message
    String SCREENSHOT_MSG_NO_WORKFLOW_FOUND = "No Workflow found";
    String SCREENSHOT_MSG_VERIFIED_WORKFLOW_IS_IN_BUCKET = "Verified workflow $1 in $2 bucket";
    String SCREENSHOT_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW = "Failed to Acknowledge $1 Workflow";
    String SCREENSHOT_MSG_SUCCESSFULLY_ACKNOWLEDGED_WORKFLOW = "Acknowledged $1 Workflow";
    String SCREENSHOT_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION = "Failed to send $1 Workflow for Clarification";
    String SCREENSHOT_MSG_SUCCESSFULLY_SENT_WORKFLOW_FOR_CLARIFICATION = "Sent $1 Workflow for Clarification";
    String SCREENSHOT_MSG_FAILED_TO_REASSIGN_WORKFLOW = "Failed to reassign $1 Workflow";
    String SCREENSHOT_MSG_SUCCESSFULLY_REASSIGN_WORKFLOW = "Reassigned $1 Workflow";
    String SCREENSHOT_MSG_FAILED_TO_SUBMIT_WORKFLOW = "Failed to Submit $1 Workflow";
    String SCREENSHOT_MSG_SUCCESSFULLY_SUBMIT_WORKFLOW = "Submitted $1 Workflow";
    String SCREENSHOT_MSG_FAILED_TO_DELEGATE_WORKFLOW = "Failed to Delegate $1 Workflow";
    String SCREENSHOT_MSG_SUCCESSFULLY_DELEGATE_WORKFLOW = "Delegated $1 Workflow";
    String SCREENSHOT_MSG_FAILED_TO_BOOKMARK_WORKFLOW = "Failed to Bookmark $1 Workflow";
    String SCREENSHOT_MSG_SUCCESSFULLY_BOOKMARK_WORKFLOW = "Bookmarked $1 Workflow";
    String SCREENSHOT_MSG_NO_USER_FOUND = "No User found";
    String SCREENSHOT_MSG_NO_GROUP_FOUND = "No Group found";
    String SCREENSHOT_MSG_NO_RESULT_FOUND = "No Result found";
    String SCREENSHOT_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION = "Failed to create Default Delegation";
    String SCREENSHOT_MSG_SUCCESSFULLY_CREATE_DEFAULT_DELEGATION = "Created Default Delegation";
    String SCREENSHOT_MSG_FAILED_TO_CREATE_USERS_DELEGATION = "Failed to create Users Delegation";
    String SCREENSHOT_MSG_SUCCESSFULLY_CREATE_USERS_DELEGATION = "Created Users Delegation";
    String SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION = "Failed to accept Delegation";
    String SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION = "Failed to reject Delegation";
    String SCREENSHOT_MSG_FAILED_TO_DELETE_DELEGATION = "Failed to delete Delegation";
    String SCREENSHOT_MSG_SUCCESSFULLY_ACCEPTED_DELEGATION = "Successfully accepted Delegation";
    String SCREENSHOT_MSG_SUCCESSFULLY_REJECTED_DELEGATION = "Successfully rejected Delegation";
    String SCREENSHOT_MSG_SUCCESSFULLY_DELETED_DELEGATION = "Successfully deleted Delegation";

    //------------------Inbox Screen---------------------

    //Bucket
    String BUCKET_TO_DO = "TO DO";
    String BUCKET_IN_PROGRESS = "IN PROGRESS";
    String BUCKET_CLOSED = "CLOSED";
    String BUCKET_TO_REVIEW = "TO REVIEW";

    //More Option's Action
    String MORE_OPTION_ACKNOWLEDGE_SELECTED = "Acknowledge Selected";
    String MORE_OPTION_ACKNOWLEDGE_ALL = "Acknowledge All";
    String MORE_OPTION_REVIEW_AND_ACCEPT_SELECTED = "Review & Accept Selected";
    String MORE_OPTION_REVIEW_AND_ACCEPT_ALL = "Review & Accept All";
    String MORE_OPTION_APPROVE_SELECTED = "Approve Selected";
    String MORE_OPTION_APPROVE_ALL = "Approve All";
    String MORE_OPTION_REVIEW_AND_ACTION_SELECTED = "Review & Action  Selected";
    String MORE_OPTION_REVIEW_AND_APPROVE_SELECTED = "Review & Approve  Selected";
    String MORE_OPTION_REVIEW_AND_APPROVE_ALL = "Review & Approve All";
    String MORE_OPTION_REVIEW_AND_ASSESS_SELECTED = "Review and Assess Selected";
    String MORE_OPTION_REVIEW_AND_ASSESS_ALL = "Review and Assess All";
    String MORE_OPTION_CLARIFY_SELECTED = "Clarify Selected";
    String MORE_OPTION_CLARIFY_ALL = "Clarify All";
    String MORE_OPTION_DELEGATE_SELECTED = "Delegate Selected";
    String MORE_OPTION_DELEGATE_ALL = "Delegate All";
    String MORE_OPTION_BOOKMARK_SELECTED = "Bookmark Selected";
    String MORE_OPTION_BOOKMARK_ALL = "Bookmark All";
    String MORE_OPTION_REASSIGN_SELECTED = "Reassign Selected";
    String MORE_OPTION_REASSIGN_ALL = "Reassign All";
    String MORE_OPTION_SUBMIT_SELECTED = "Submit Selected";
    String MORE_OPTION_SUBMIT_ALL = "Submit All";
    String MORE_OPTION_RESPOND_SELECTED = "Respond Selected";
    String MORE_OPTION_REQUEST_FOR_CLARIFICATION_SELECTED = "Request for Clarification Selected";

    //------------------ Inbox Detail View Screen ---------------------

    String INBOX_DETAIL_PREV_ACTOR_COMMENTS_CELL = "Prev Actor Comments";
    String INBOX_DETAIL_ACKNOWLEDGEMENT_COMMENTS_CELL = "Acknowledgement Comments";
    String INBOX_DETAIL_COMPLIANCE_COMMENTS_CELL = "Compliance Comments";
    String INBOX_DETAIL_VDO_COMMENTS_CELL = "VDO Comments";
    String INBOX_DETAIL_MRO_OR_MTCR_UPLOADER_COMMENTS_CELL = "MRO/MTCR Uploader Comments";
    String INBOX_DETAIL_CURR_ACTOR_TYPE = "Curr Actor Type";

    //-------- CE Workflow ----------
    //MTCR User
    String INBOX_DETAIL_ISSUE_FLAG_COMMENT_CELL = "Issue Flagged By (MTCR)";
    String INBOX_DETAIL_RISK_ASSESSMENT_COMMENT_CELL = "Risk Assessment and Actions Taken to Mitigate Risk";
    String INBOX_DETAIL_EXPLANATION_COMMENT_CELL = "Explanation / Details of Control Break Down";
    String INBOX_DETAIL_CONTROL_BREAKDOWN_COMMENT_CELL = "Type of Control Breakdown";
    String INBOX_DETAIL_OUTCOME_COMMENT_CELL = "Outcome of the Excess";
    String INBOX_DETAIL_GROUP_REMARK_COMMENT_CELL = "TCRM Group Remarks";
    //FO User
    String INBOX_DETAIL_FO_JUSTIFICATION_COMMENT_CELL = "Front Office Justification for the disciplinary";
    String INBOX_DETAIL_PREVENT_RECURRENCE_COMMENT_CELL = "How will you prevent recurrence";
    String INBOX_DETAIL_SUPERVISOR_REMARK_CELL = "Supervisor Remarks";
    String INBOX_DETAIL_JUSTIFICATION_FOR_DISCIPLINARY_ACTION_COMMENT_CELL = "Justification for disciplinary action";
    String INBOX_DETAIL_HOW_TO_PREVENT_RECURRENCE_COMMENT_CELL = "How to prevent recurrence";
    String INBOX_DETAIL_REMEDIATION_ACTION_COMMENT_CELL = "Remediation action";
    String INBOX_DETAIL_VDO_COMMENT_CELL = "VDO Comments";


    String PREV_ACTOR_TYPE_ROLE_MTCR_OR_MRO_UPLOADER = "MTCR/MRO Uploader";
    String PREV_ACTOR_TYPE_ROLE_VOLCKER_DESK_OWNER = "VOLCKERDESKOWNER";

    String CURR_ACTOR_TYPE_CNA_PERFORMER_LM = "CNAPERFORMERLM";
    String CURR_ACTOR_TYPE_CNA_PERFORMER = "CNAPERFORMER";
    String CURR_ACTOR_TYPE_OMR_PERFORMER_LM = "OMRPERFORMERLM";
    String CURR_ACTOR_TYPE_OMR_PERFORMER = "OMRPERFORMER";
    String CURR_ACTOR_TYPE_PC_USER = "PCUSER";
    String CURR_ACTOR_TYPE_MO_USER = "MOUSER";
    String CURR_ACTOR_TYPE_OTHER_USER = "OTHERUSER";
    String CURR_ACTOR_TYPE_MRO_MTCR_GT_USER = "MRO/MTCR/GT USER";
    String CURR_ACTOR_TYPE_VC_USER = "VC USER";
    String CURR_ACTOR_TYPE_COMPLIANCE = "COMPLIANCE";

    String CURR_ACTOR_GROUP_PC_GBS = "PC GBS";

    //----------------- Delegation Screen ---------------------

    String DELEGATION_TYPE_DEFAULT = "Default";
    String DELEGATION_TYPE_USER = "User";
    String DELEGATION_TYPE_HIERARCHY = "Hierarchy";
    String DELEGATION_TYPE_DESK_OR_COUNTRY = "Desk/Country";
    String DELEGATION_TYPE_CE_VE_USER = "User";
    String DELEGATION_TYPE_PORTFOLIO = "Portfolio";
    String DELEGATION_TYPE_REPORT_LABEL = "Report Label";
    String DELEGATION_OPTION_ALL = "ALL";
    String DELEGATION_OPTION_FX = "FX";
    String DELEGATION_OPTION_FX_CASH = "FX Cash";
    String DELEGATION_OPTION_FX_ASA = "FX ASA";
    String DELEGATION_OPTION_CMS = "CMS";
    String DELEGATION_OPTION_ABS_STRUCTURED = "ABS - Structured";
    String DELEGATION_OPTION_ABS_STRUCTERED_MTM = "ABS - Structured - MTM";

    String DELEGATION_STATUS_PENDING = "Pending";
    String DELEGATION_STATUS_ACCEPTED = "Accepted";
    String DELEGATION_STATUS_REJECTED = "Rejected";

    String DELEGATION_COUNTRY_JAPAN = "JAPAN";
    String DELEGATION_COUNTRY_KOREA = "KOREA, REPUBLIC OF";

    //----------------- Overview Screen ---------------------

    //Workflow Status
    String STATUS_OVERDUE = "overdue";
    String STATUS_DUE = "due";
    String STATUS_OPEN ="open";

    //Workflow Type
    String WORKFLOW_CNA = "Cancel & Amend";
    String WORKFLOW_OMR = "Off Market Rate";
    String WORKFLOW_PNL = "Profit & Loss";
    String WORKFLOW_GT = "Group Treasury";
    String WORKFLOW_GMR = "Group Market Risk";
    String WORKFLOW_IPV = "Independent Price Verification";
    String WORKFLOW_FVA = "Fair Value Adjustment";
    String WORKFLOW_CE = "Credit Excess";
    String WORKFLOW_VE = "Volcker RENTD Excess";
    String WORKFLOW_TM = "Trader Mandate";

    //------------------- Action Screen --------------------------------

    //Picker Value
    String LATE_CODE_DEADLINE_MISSED = "Deadline missed";
    String CE_SEVERITY_HIGH = "High";
    String CE_POTENTIAL_LOSS_YES = "Yes";
    String CE_DISCIPLINARY_ACTION_DISMISSAL = "Dismissal";
    String CE_LATE_CODE_OTHERS = "OTHERS";
    String VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING = "Coaching / Counseling";
    String VE_LATE_CODE_INVESTIGATION_WITH_HR = "Investigation with HR";
    String ACKNOWLEDGEMENT_CODE_HRR_DEAL = "HRR Deal";

    //PC Group

    String PC_GRP_PC_ALM = "PC ALM - Treasury";
    String PC_GRP_PC_GBS = "PC GBS";

    //Form's Label
    String FORM_LABEL_LATE_CODE = "Late Code";
    String FORM_LABEL_COMMENTS = "Comments";
    String FORM_LABEL_VDO_COMMENTS = "VDO Comments*";
    String FORM_LABEL_RESPONSE_COMMENTS = "Response Comments";
    String FORM_LABEL_LATE_COMMENTS = "Late Comments";
    String FORM_LABEL_LATE_RESPONSE_CODE = "Late Response Code";
    String FORM_LABEL_VE_LATE_RESPONSE_CODE = "Late Response Code*";
    String FORM_LABEL_LATE_RESPONSE_COMMENTS = "Late Response Comments";
    String FORM_LABEL_ACKNOWLEDGE_CODE = "Acknowledgement Code";
    String FORM_LABEL_ACKNOWLEDGEMENT_COMMENTS = "Acknowledgement Comments";
    String FORM_LABEL_CE_SEVERITY = "Severity*";
    String FORM_LABEL_CE_ISSUE_FLAGGED_BY_MTCR = "Issue Flagged By (MTCR)*";
    String FORM_LABEL_CE_RISK_ASSESSMENT_AND_ACTIONS_TAKEN_TO_MITIGATE_RISK = "Risk Assessment and Actions Taken to Mitigate Risk*";
    String FORM_LABEL_CE_EXPLANATION_OR_DETAILS_CONTROL_BREAK_DOWN = "Explanation / Details of Control Break Down*";
    String FORM_LABEL_CE_TYPE_OF_CONTROL_BREAKDOWN = "Type of Control Breakdown*";
    String FORM_LABEL_CE_ANY_POTENTIAL_LOSS = "Any Potential Loss*";
    String FORM_LABEL_CE_OUTCOME_OF_THE_EXCESS = "Outcome of the Excess*";
    String FORM_LABEL_CE_TCRM_GROUP_REMARKS = "TCRM Group Remarks*";
    String FORM_LABEL_CE_DISCIPLINARY_ACTION_TAKEN = "Disciplinary Action Taken*";
    String FORM_LABEL_CE_FRONT_OFFICE_JUSTIFICATION_FOR_THE_DISCIPLINARY = "Front Office Justification for the disciplinary*";
    String FORM_LABEL_CE_HOW_WILL_YOU_PREVENT_RECURRENCE = "How will you prevent recurrence*";
    String FORM_LABEL_CE_SUPERVISOR_REMARKS = "Supervisor Remarks*";
    String FORM_LABEL_DISCIPLINARY_ACTION_TAKEN = "Disciplinary Action taken*";
    String FORM_LABEL_JUSTIFICATION_FOR_DISCIPLINARY_ACTION = "Justification for Disciplinary action*";
    String FORM_LABEL_HOW_TO_PREVENT_RECURRENCE = "How to prevent recurrence";
    String FORM_LABEL_REMEDIATION_ACTION = "Remediation Action*";
    String FORM_LABEL_SUPERVISOR_COMMENTS = "Supervisor Comments (Optional)";
    String FORM_LABEL_PSID_OR_NAME = "PSID/Name";
    String FORM_LABEL_ESCALATE = "Escalate";
    String FORM_LABEL_DISPUTE = "Dispute";
    String FORM_LABEL_TO_GROUP = "To Group";

    //------------------ Clarification Option Screen ----------------

    String CLARIFICATION_OPTION_CNA_LM = "Cancel & Amend Performer Line Manager";
    String CLARIFICATION_OPTION_CNA_PERFORMER = "Cancel & Amend Performer";
    String CLARIFICATION_OPTION_OMR_LM = "Off Market Rate Performer Line Manager";
    String CLARIFICATION_OPTION_OMR_PERFORMER = "Off Market Rate Performer";
    String CLARIFICATION_OPTION_PC = "Product Control";
    String CLARIFICATION_OPTION_MO = "Middle Office";
    String CLARIFICATION_OPTION_SEND_TO = "Send to";
    String CLARIFICATION_OPTION_MRO_MTRC_GT = "MRO/MTCR/GT";
    String CLARIFICATION_OPTION_VALUATION_CONTROL_USER = "Valuation Control User";
    String CLARIFICATION_OPTION_VOLCKER_DESK_OWNER = "Volcker Desk Owner";
    String CLARIFICATION_OPTION_MTCR_RISK_MANAGER = "MTCR Risk Manager";
    String CLARIFICATION_OPTION_MTCR_OR_MRO_UPLOADER = "MTCR/MRO Uploader";
    String CLARIFICATION_OPTION_SEND_TO_OTHERS_VE = "Send To Others";
    String CLARIFICATION_OPTION_VOLCKER_COMPLIANCE = "Volcker Compliance";

    //------------------ Clarification Option Screen ----------------
    String RESPOND_OPTION_RESPOND_SELECTED = "Respond Selected";
    String RESPOND_OPTION_EDIT_AND_RESPOND = "Edit and Respond";
    String RESPOND_OPTION_RESPOND = "Respond";

    //------------------ Driver Setup --------------------

    String DRIVER_URL = "http://127.0.0.1:4723/wd/hub";

    //Capability Name
    String CAPABILITY_NAME_DEVICE_NAME = "deviceName";
    String CAPABILITY_NAME_PLATFORM_NAME = "platformName";
    String CAPABILITY_NAME_PLATFORM_VERSION = "platformVersion";
    String CAPABILITY_NAME_UDID = "udid";
    String CAPABILITY_NAME_AUTOMATION_NAME = "automationName";
    String CAPABILITY_NAME_LAUNCH_TIMEOUT = "launchTimeout";
    String CAPABILITY_NAME_APP = "app";
    String CAPABILITY_NAME_NEW_COMMAND_TIMEOUT = "newCommandTimeout";
    String CAPABILITY_NAME_BUNDLE_ID = "bundleId";
    String CAPABILITY_NAME_USE_NEW_WDA = "useNewWDA";

    //Capability Value
    String CAPABILITY_VALUE_DEVICE_NAME = "iPhone";
    String CAPABILITY_VALUE_PLATFORM_NAME = "iOS";
    String CAPABILITY_VALUE_PLATFORM_VERSION = "11.1.1";
    String CAPABILITY_VALUE_UDID_IPHONE6 = "55bd37ddcebbd54dbac0af44a34e2815a841e6b2";
    String CAPABILITY_VALUE_UDID_IPHONE7 = "68a77c4e8ca8c34fa0957c8d39f1f445f664cb18";
    String CAPABILITY_VALUE_UDID_IPHONE7_NEW = "453c0f8d6c435754717bb056e5fe46220c2e7ef0";
    String CAPABILITY_VALUE_AUTOMATION_NAME = "XCUITest";
    String CAPABILITY_VALUE_LAUNCH_TIMEOUT = "100000";
    String CAPABILITY_VALUE_APP_UAT = "/Users/optimum/Desktop/UAT Build/FMDashboard.app";
    String CAPABILITY_VALUE_APP_SIT = "/Users/optimum/Desktop/SIT Build/FMDashboard.app";
    int CAPABILITY_VALUE_NEW_COMMAND_TIMEOUT = 100000;
    String CAPABILITY_VALUE_BUNDLE_ID = "com.ops.ios.supervisorydashboard";
    String CAPABILITY_VALUE_BUNDLE_ID_ARI = "com.ops.fms.supervisorydashboard";
    boolean CAPABILITY_VALUE_USE_NEW_WDA = false; // so that it wont reinstall WDA every time
}