package com.kart.catalog.common.validation;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Set;

public final class GenericValidators {

    private static final int PRODUCT_MAX_PAGE_SIZE = 10;
    private static final Set<String> PRODUCT_ALLOWED_STATUS = Set.of("ENABLED", "DISABLED");
    private static final Set<String> PRODUCT_ALLOWED_SORT_BY = Set.of("createdAt", "updatedAt", "price", "name");
    private static final Set<String> PRODUCT_ALLOWED_SORT_DIRECTION = Set.of("asc", "desc");

    private static void validateProductStatus(String status) {
        if(status != null) {
            if(!PRODUCT_ALLOWED_STATUS.contains(status)) {
                throw new IllegalArgumentException("Invalid status: " + status);
            }
        }
    }

    private static void validateProductSortBy(String sortBy) {
        if(sortBy != null) {
            if(!PRODUCT_ALLOWED_SORT_BY.contains(sortBy)) {
                throw new IllegalArgumentException("Invalid sortBy argument: " + sortBy);
            }
        }
    }

    private static void validateProductSortDirection(String sortDirection) {
        if(sortDirection != null) {
            if(!PRODUCT_ALLOWED_SORT_DIRECTION.contains(sortDirection)) {
                throw new IllegalArgumentException("Invalid sortDirection argument: " + sortDirection);
            }
        }
    }

    private static void validateProductMinPrice(BigDecimal minPrice) {
        if(minPrice != null) {
            if(minPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Invalid minPrice: " + minPrice);
            }
            if(minPrice.compareTo(BigDecimal.valueOf(9999999999.99))>0) {
                throw new IllegalArgumentException("minPrice is outside allowed digit limit: " + minPrice);
            }
        }
    }

    private static void validateProductMaxPrice(BigDecimal maxPrice) {
        if(maxPrice != null) {
            if(maxPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Invalid maxPrice: " + maxPrice);
            }
            if(maxPrice.compareTo(BigDecimal.valueOf(9999999999.99))>0) {
                throw new IllegalArgumentException("maxPrice is outside allowed digit limit: " + maxPrice);
            }
        }
    }

    private static void validateProductMinMaxComparison(BigDecimal minPrice, BigDecimal maxPrice) {
        if(minPrice != null && maxPrice !=null) {
            if(minPrice.compareTo(maxPrice)>0) {
                throw new IllegalArgumentException("minPrice can not be more than maxPrice");
            }
        }
    }

    private static void validatePage(int page) {
        if(page<0) {
            throw new IllegalArgumentException("Page can not be negative");
        }
    }

    private static void validateProductSize(int size) {
        if(size<=0 || size>PRODUCT_MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size can not be negative and must not exceed " + PRODUCT_MAX_PAGE_SIZE);
        }
    }

    public static String normalizeProductNameSearchString(String name) {
        String lowerCasedSearchProductName = "";
        if(name != null) {
            lowerCasedSearchProductName = name.toLowerCase(Locale.ROOT);
        }
        return lowerCasedSearchProductName;
    }

    public static void validateProductFilters(
            String status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        validateProductStatus(status);
        validateProductMinPrice(minPrice);
        validateProductMaxPrice(maxPrice);
        validateProductMinMaxComparison(minPrice, maxPrice);
        validatePage(page);
        validateProductSize(size);
        validateProductSortBy(sortBy);
        validateProductSortDirection(sortDirection);
    }

}
