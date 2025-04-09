package com.epm.gestepm.model.shares.noprogrammed.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.shares.noprogrammed.checker.NoProgrammedShareChecker;
import com.epm.gestepm.model.shares.noprogrammed.dao.NoProgrammedShareDao;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFilter;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.decorator.NoProgrammedSharePostCreationDecorator;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSToNoProgrammedShareByIdFinder;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSToNoProgrammedShareDto;
import com.epm.gestepm.model.shares.noprogrammed.service.mapper.MapNPSToNoProgrammedShareFilter;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFilterDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ConstructionShareServiceImplTest {

    private final static Long OFFSET = 0L;

    private final static Long LIMIT = 10L;

    private final static Integer NO_PROGRAMMED_SHARE_ID = 1;

    @Mock
    private MapNPSToNoProgrammedShareDto mapper;

    @Mock
    private MapNPSToNoProgrammedShareFilter filterMapper;

    @Mock
    private MapNPSToNoProgrammedShareByIdFinder finderMapper;

    @Mock
    private NoProgrammedShareChecker noProgrammedShareChecker;

    @Mock
    private NoProgrammedShareDao noProgrammedShareDao;

    @Mock
    private NoProgrammedSharePostCreationDecorator noProgrammedSharePostCreationDecorator;

    @InjectMocks
    private NoProgrammedShareServiceImpl noProgrammedShareService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldListNoProgrammedShares() {
        final NoProgrammedShareFilterDto source = new NoProgrammedShareFilterDto();
        source.setIds(List.of(NO_PROGRAMMED_SHARE_ID));

        final NoProgrammedShareFilter filter = new NoProgrammedShareFilter();
        filter.setIds(List.of(NO_PROGRAMMED_SHARE_ID));

        when(this.filterMapper.from(source)).thenReturn(filter);

        final NoProgrammedShare dbResult = new NoProgrammedShare();
        dbResult.setId(NO_PROGRAMMED_SHARE_ID);

        when(this.noProgrammedShareDao.list(filter)).thenReturn(List.of(dbResult));

        final NoProgrammedShareDto dto = new NoProgrammedShareDto();
        dto.setId(NO_PROGRAMMED_SHARE_ID);

        when(this.mapper.from(dbResult)).thenReturn(dto);

        final List<NoProgrammedShareDto> expected = List.of(dto);
        final List<NoProgrammedShareDto> result = this.noProgrammedShareService.list(source);

        assertNotNull(result);
        assertEquals(expected, result);
        verify(this.noProgrammedShareDao, times(1)).list(filter);
    }

    @Test
    void shouldPageNoProgrammedShares() {
        final NoProgrammedShareFilterDto source = new NoProgrammedShareFilterDto();
        source.setIds(List.of(NO_PROGRAMMED_SHARE_ID));

        final NoProgrammedShareFilter filter = new NoProgrammedShareFilter();
        filter.setIds(List.of(NO_PROGRAMMED_SHARE_ID));

        when(this.filterMapper.from(source)).thenReturn(filter);

        final NoProgrammedShare dbResult = new NoProgrammedShare();
        dbResult.setId(NO_PROGRAMMED_SHARE_ID);

        final Page<NoProgrammedShare> page = new Page<>();
        page.setOffset(OFFSET);
        page.setLimit(LIMIT);
        page.setTotal(1L);
        page.setContent(List.of(dbResult));

        when(this.noProgrammedShareDao.list(filter, OFFSET, LIMIT)).thenReturn(page);

        final NoProgrammedShareDto dto = new NoProgrammedShareDto();
        dto.setId(NO_PROGRAMMED_SHARE_ID);

        when(this.mapper.from(dbResult)).thenReturn(dto);

        final Page<NoProgrammedShareDto> expected = new Page<>();
        expected.setOffset(OFFSET);
        expected.setLimit(LIMIT);
        expected.setTotal(1L);
        expected.setContent(List.of(dto));

        final Page<NoProgrammedShareDto> result = this.noProgrammedShareService.list(source, OFFSET, LIMIT);

        assertNotNull(result);
        assertEquals(expected, result);
        verify(this.noProgrammedShareDao, times(1)).list(filter, OFFSET, LIMIT);
    }

    @Test
    void shouldFindNoProgrammedShares() {
        final NoProgrammedShareByIdFinderDto source = new NoProgrammedShareByIdFinderDto();
        source.setId(NO_PROGRAMMED_SHARE_ID);

        final NoProgrammedShareByIdFinder finder = new NoProgrammedShareByIdFinder();
        finder.setId(NO_PROGRAMMED_SHARE_ID);

        when(this.finderMapper.from(source)).thenReturn(finder);

        final NoProgrammedShare dbResult = new NoProgrammedShare();
        dbResult.setId(NO_PROGRAMMED_SHARE_ID);

        when(this.noProgrammedShareDao.find(finder)).thenReturn(Optional.of(dbResult));

        final NoProgrammedShareDto expected = new NoProgrammedShareDto();
        expected.setId(NO_PROGRAMMED_SHARE_ID);

        when(this.mapper.from(dbResult)).thenReturn(expected);

        final Optional<NoProgrammedShareDto> result = this.noProgrammedShareService.find(source);

        assertNotNull(result);
        assertEquals(Optional.of(expected), result);
        verify(this.noProgrammedShareDao, times(1)).find(finder);
    }
}
