WITH base_data AS
    (SELECT collateralMatch.vid                  AS VID
          , collateralMatch.old_collateral_no    AS OLD_COLLATERAL_NO
          , collateralMatch.COLLATERAL_PRODUCT_CODE  AS COLLATERAL_PRODUCT_CODE
          , collateralProduct.TITLE              AS COLLATERAL_PRODUCT_TITLE
          , collateralMatch.owner_organ_code     AS OWNER_ORGAN_CODE
          , collateralMatch.ACCOUNT_NO           AS ACCOUNT_NO
          , collateralMatch.OWNER_ORGAN_ID       AS OWNER_ORGAN_ID
          , committedMatch.full_name             AS FULL_NAME
          , committedMatch.identifier_no         AS IDENTIFIER_NO
          , committedMatch.customer_no           AS CUSTOMER_NO
          , collateralMatch.COLLATERAL_AMOUNT    AS COLLATERAL_AMOUNT
          , mortgageJson.OLD_COLLATERAL_AMOUNT   AS OLD_COLLATERAL_AMOUNT
         /* تاریخ اصلی برای نمایش */
          , collateralMatch.collateral_due_date  AS COLLATERAL_DUE_DATE
          , mortgageJson.OLD_COLLATERAL_DUE_DATE AS OLD_COLLATERAL_DUE_DATE
         /* تاریخ نرمال‌شده فقط برای مقایسه */
          , REPLACE(collateralMatch.collateral_due_date, '/','') AS COLLATERAL_DUE_DATE_RAW
          , REPLACE(mortgageJson.OLD_COLLATERAL_DUE_DATE,'/','') AS OLD_COLLATERAL_DUE_DATE_RAW
         /* تاریخ اصلی برای نمایش */
          , collateralMatch.issue_date           AS ISSUE_DATE
          , mortgageJson.OLD_ISSUE_DATE          AS OLD_ISSUE_DATE
         /* تاریخ نرمال‌شده فقط برای مقایسه */
          , REPLACE(collateralMatch.issue_date,'/','') AS ISSUE_DATE_RAW
          , REPLACE(mortgageJson.OLD_ISSUE_DATE,'/','') AS OLD_ISSUE_DATE_RAW
          , collateralMatch.doc_no               AS DOC_NO
          , mortgageJson.OLD_DOC_NO              AS OLD_DOC_NO
          , assetMatch.asset_amount              AS ASSET_AMOUNT
          , assetJson.OLD_ASSET_AMOUNT           AS OLD_ASSET_AMOUNT
          , contractMatch.CONTRACT_NO            AS CONTRACT_NO
          , contractJson.OLD_CONTRACT_NO         AS OLD_CONTRACT_NO
          , contractMatch.ALLOCATED_AMOUNT       AS ALLOCATED_AMOUNT
          , contractJson.OLD_ALLOCATED_AMOUNT    AS OLD_ALLOCATED_AMOUNT
     FROM clt.tb_vas_ldr_collateral_match collateralMatch
              INNER JOIN clt.tb_vas_ldr_change_info_match changeInfo
                         ON changeInfo.vid = collateralMatch.vid
              INNER JOIN clt.tb_vas_ldr_committed_match committedMatch
                         ON committedMatch.vid = collateralMatch.vid
                             AND committedMatch.COMMITTED_ROLE_CODE = 1
              INNER JOIN CLT.TB_COLLATERAL_PRODUCT collateralProduct
                         ON collateralProduct.CODE =
                            collateralMatch.COLLATERAL_PRODUCT_CODE
              INNER JOIN clt.tb_vas_ldr_asset_match assetMatch
                         ON assetMatch.vid = collateralMatch.vid
              INNER JOIN clt.tb_vas_ldr_contract_match contractMatch
                         ON contractMatch.vid = collateralMatch.vid
    /* CHANGEMORTGAGEINFO فقط یک بار خوانده می‌شود */
    OUTER APPLY JSON_TABLE(
    changeInfo.CHANGEMORTGAGEINFO
   , '$'  COLUMNS  ( OLD_COLLATERAL_AMOUNT  NUMBER PATH '$.collateralAmount'
   , OLD_COLLATERAL_DUE_DATE VARCHAR2(200)  PATH '$.collateralDueDate'
   , OLD_ISSUE_DATE VARCHAR2(200) PATH '$.issueDate'
   , OLD_DOC_NO VARCHAR2(200)  PATH '$.docNo' )
    ) mortgageJson
    /* CHANGE_ASSET_INFO */
    OUTER APPLY JSON_TABLE(
    changeInfo.CHANGE_ASSET_INFO
   , '$[0]'  COLUMNS  ( OLD_ASSET_AMOUNT  NUMBER  PATH '$.assetAmount'   )
    ) assetJson
    /* CHANGE_CONTRACT_INFO فقط یک بار خوانده می‌شود */
    OUTER APPLY JSON_TABLE(
    changeInfo.CHANGE_CONTRACT_INFO
   , '$' COLUMNS  ( OLD_CONTRACT_NO VARCHAR2(4000) PATH '$.contractNo'
   , OLD_ALLOCATED_AMOUNT  NUMBER PATH '$.allocatedAmount'
    ) ) contractJson
    )
   , diff_data AS
   (
SELECT
    b.*, GREATEST(
    DECODE(b.COLLATERAL_AMOUNT, b.OLD_COLLATERAL_AMOUNT, 0, 1)
     , DECODE(b.COLLATERAL_DUE_DATE_RAW, b.OLD_COLLATERAL_DUE_DATE_RAW, 0, 1)
        , DECODE(b.ISSUE_DATE_RAW, b.OLD_ISSUE_DATE_RAW, 0, 1)
        , DECODE(b.DOC_NO, b.OLD_DOC_NO, 0, 1)
        , DECODE(b.ASSET_AMOUNT, b.OLD_ASSET_AMOUNT, 0, 1 )
        , DECODE(b.CONTRACT_NO, b.OLD_CONTRACT_NO, 0, 1)
        , DECODE(b.ALLOCATED_AMOUNT, b.OLD_ALLOCATED_AMOUNT, 0, 1 )
    ) AS HAS_DIFF
FROM base_data b
  )
SELECT VID
     , OLD_COLLATERAL_NO
     , COLLATERAL_PRODUCT_CODE
     , COLLATERAL_PRODUCT_TITLE
     , OWNER_ORGAN_CODE
     , FULL_NAME
     , IDENTIFIER_NO
     , CUSTOMER_NO
     , COLLATERAL_AMOUNT
     , OLD_COLLATERAL_AMOUNT
     , COLLATERAL_DUE_DATE
     , OLD_COLLATERAL_DUE_DATE
     , ISSUE_DATE
     , OLD_ISSUE_DATE
     , DOC_NO
     , OLD_DOC_NO
     , ASSET_AMOUNT
     , OLD_ASSET_AMOUNT
     , CONTRACT_NO
     , OLD_CONTRACT_NO
     , ALLOCATED_AMOUNT
     , OLD_ALLOCATED_AMOUNT
FROM diff_data
WHERE HAS_DIFF = 1;