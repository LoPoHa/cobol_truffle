       IDENTIFICATION DIVISION.
       PROGRAM-ID. SIMPLECALLED.
      *
       ENVIRONMENT DIVISION.
      *
       DATA DIVISION.
       LINKAGE SECTION.
       COPY BASICDEFINITIONS.
      *
       PROCEDURE DIVISION.
      *
       MAIN SECTION.
      *
          MOVE "HELLO WORLD" TO COPY-STRING.
          MOVE "12345678"    TO COPY-NUMBER.
      *
       PROG-EX.
           EXIT PROGRAM.
      *
       END PROGRAM SIMPLECALLED.