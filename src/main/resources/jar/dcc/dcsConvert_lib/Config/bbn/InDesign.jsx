var doc = app.open(File(arguments[0]));
doc.exportFile(ExportFormat.pdfType, File(arguments[1]),false);
doc.close(SaveOptions.no);