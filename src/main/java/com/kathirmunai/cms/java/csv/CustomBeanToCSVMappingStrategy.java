package com.kathirmunai.cms.java.csv;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CustomBeanToCSVMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {

	@Override
	public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {

		String[] headersAsPerFieldName = getFieldMap().generateHeader(bean); // header name based on field name
		String[] header = new String[headersAsPerFieldName.length];

		for (int i = 0; i <= headersAsPerFieldName.length - 1; i++) {

			BeanField<T, Integer> beanField = findField(i);
			String columnHeaderName = extractHeaderName(beanField); // header name based on @CsvBindByName annotation

			if (columnHeaderName.isEmpty()) // No @CsvBindByName is present
				columnHeaderName = headersAsPerFieldName[i]; // defaults to header name based on field name

			header[i] = columnHeaderName;
		}
		headerIndex.initializeHeaderIndex(header);

		return header;
	}

	private String extractHeaderName(final BeanField<T, Integer> beanField) {
		if (beanField == null || beanField.getField() == null
				|| beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class).length == 0) {
			return StringUtils.EMPTY;
		}

		final CsvBindByName bindByNameAnnotation = beanField.getField()
				.getDeclaredAnnotationsByType(CsvBindByName.class)[0];
		return bindByNameAnnotation.column();
	}
}