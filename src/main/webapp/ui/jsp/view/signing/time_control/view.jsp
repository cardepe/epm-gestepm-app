<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="breadcrumbs">
    <div class="breadcrumbs-inner">
        <div class="row m-0 align-items-center">
            <div class="col-md-12 col-lg-8">
                <div class="page-header float-left">
                    <div class="page-title">
                        <h1 class="text-uppercase"><spring:message code="time.control.title"/></h1>
                    </div>
                </div>
            </div>
            <div class="col-md-12 col-lg-4">
                <form>
                    <div class="form-row">
                        <div class="col">
                            <select id="monthDropdown" name="month" class="form-control form-control-sm">
                                <option disabled value="" selected="selected">
                                    <spring:message code="holidays.admin.month.placeholder"/>
                                </option>
                                <c:forEach items="${months}" var="month">
                                    <option value="${month.key}">
                                        <spring:message code="${month.value}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col">
                            <select id="yearDropdown" name="year" class="form-control form-control-sm">
                                <option disabled value="" selected="selected">
                                    <spring:message code="time.control.year.placeholder"/>
                                </option>
                                <c:forEach items="${years}" var="year">
                                    <option value="${year}">
                                        <spring:message code="${year}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <button id="resetButton" type="button" class="btn btn-danger btn-sm" style="margin-right: 3px"
                                onclick="resetTimeControl()">
                            <span class="fas fa-sync" style="font-size: 11px"></span>
                        </button>

                        <button id="nextButton" type="button" class="btn btn-primary btn-sm"
                                onclick="updateTimeControl()">
                            <span class="fas fa-search" style="font-size: 11px"></span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="clearfix"></div>

<div class="content">
    <div class="row h-100">
        <div class="col h-100">
            <div class="card">
                <div class="card-body">
                    <div class="title mb-4">
                        <spring:message code="time.control.title"/>
                    </div>

                    <div class="table-responsive">
                        <table id="dTable" class="table table-striped table-borderer dataTable">
                            <caption class="d-none">
                                NO SONAR
                            </caption>
                            <thead>
                            <tr>
                                <th id="thDate"><spring:message code="time.control.date"/></th>
                                <th id="thUsername"><spring:message code="time.control.username"/></th>
                                <th id="thReason"><spring:message code="time.control.reason"/></th>
                                <th id="thStartHour"><spring:message code="time.control.start.hour"/></th>
                                <th id="thEndHour"><spring:message code="time.control.end.hour"/></th>
                                <th id="thBreaks"><spring:message code="time.control.breaks"/></th>
                                <th id="thJourney"><spring:message code="time.control.journey"/></th>
                                <th id="thTotalHours"><spring:message code="time.control.total.hours"/></th>
                                <th id="thDifference"><spring:message code="time.control.difference"/></th>
                                <th id="thActions" class="all"><spring:message code="time.control.table.actions"/></th>
                            </tr>
                            </thead>
                            <tfoot>
                            <tr>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                                <th></th>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:useBean id="jspUtil" class="com.epm.gestepm.modelapi.common.utils.JspUtil"/>

<script>

    $(document).ready(function () {

        /* Datatables */
        dTable = $('#dTable').DataTable({
            "lengthChange": false,
            "searching": false,
            "responsive": false,
            "processing": true,
            "serverSide": true,
            "iDisplayLength": 31,
            "ordering": false,
            "ajax": "/signing/personal/time-control/dt${empty userDetailId ? '' : '?user='}${userDetailId}",
            "rowId": "tc_id",
            "language": {
                "url": "/ui/static/lang/datatables/${locale}.json"
            },
            "columns": [
                {"data": "tc_date"},
                {"data": "tc_username"},
                {"data": "tc_reason"},
                {"data": "tc_startHour"},
                {"data": "tc_endHour"},
                {"data": "tc_breaks"},
                {"data": "tc_journey"},
                {"data": "tc_totalHours"},
                {"data": "tc_difference"},
                {"data": null}
            ],
            "columnDefs": [
                {"className": "text-center", "orderable": false, "targets": "_all"},
                {"defaultContent": "", "targets": [3, 4]},
                {
                    "render": function (data, type, row) {
                        moment.locale('${locale}');
                        var fieldText = moment(data).format('dddd DD/MM/YYYY');
                        fieldText = fieldText.charAt(0).toUpperCase() + fieldText.slice(1);
                        return fieldText.split(' ')[0] + '<br>' + fieldText.split(' ')[1];
                    },
                    "targets": 0
                },
                {
                    "render": function (data, type, row) {
                        var type = data.charAt(0);
                        var html = '';

                        if (type === "1") {
                            html = '<div class="gray-circle"></div>';
                        } else if (type === "2") {
                            html = '<div class="green-circle"></div>';
                        }

                        return html + data.substring(1);
                    },
                    "targets": 2
                },
                {
                    "render": function (data, type, row) {
                        if (!data) {
                            return '';
                        }

                        return moment(data).format('HH:mm:ss');
                    },
                    "targets": [3, 4]
                },
                {
                    defaultContent: '-',
                    targets: [5, 7]
                },
                {
                    "render": function (data, type, row) {
                        if (!data) {
                            return null;
                        }

                        if (data.startsWith("-")) {
                            return '<span style="color: red">' + data.replace(/-/g, '') + '</span>';
                        }

                        return data
                    },
                    defaultContent: '-',
                    targets: 8
                },
                {"defaultContent": "${tableActionButtons}", "targets": -1}
            ],
            "dom": "<'top'>rt<'bottom'><'clear'>",
            "drawCallback": function (settings, json) {
                parseActionButtons();
            },
            "footerCallback": function (row, data, start, end, display) {

                let api = this.api();

                let hoursToMinutes = function (i) {

                    let totalMinutes = 0;

                    if (typeof i === 'string' && i) {

                        let hours = Number(i.split(":")[0]);
                        let minutes = Number(i.split(":")[1]);

                        if (hours < 0 || minutes < 0) {

                            if (hours > 0) {
                                hours = hours * -1;
                            }
                            if (minutes > 0) {
                                minutes = minutes * -1;
                            }
                        }

                        totalMinutes += (hours * 60) + minutes;

                    } else if (i && typeof i === 'number') {
                        return i;
                    }

                    return totalMinutes;
                };

                let totalWorkedMins = api
                    .column(7)
                    .data()
                    .filter(value => value)
                    .reduce(function (a, b) {
                        return hoursToMinutes(a) + hoursToMinutes(b);
                    }, 0);

                let totalRemainingMins = api
                    .column(8)
                    .data()
                    .filter(value => value)
                    .reduce(function (a, b) {
                        return hoursToMinutes(a) + hoursToMinutes(b);
                    }, 0);

                let totalHoursWorked = Math.floor(totalWorkedMins / 60);
                let totalMinsWorked = totalWorkedMins % 60;

                let totalRemainingMinsNegative = totalRemainingMins < 0;

                if (totalRemainingMinsNegative) {
                    totalRemainingMins = totalRemainingMins * -1;
                }

                let totalHoursRemaining = Math.floor(totalRemainingMins / 60);
                let totalMinsRemaining = totalRemainingMins % 60;

                let totalTimeWorked = padTo2Digits(totalHoursWorked) + ':' + padTo2Digits(totalMinsWorked);
                let totalTimeRemaining = padTo2Digits(totalHoursRemaining) + ':' + padTo2Digits(totalMinsRemaining);

                if (totalRemainingMinsNegative) {
                    totalTimeRemaining = '<span style="color: red">' + totalTimeRemaining + '</span>';
                }

                $(api.column(6).footer()).html('Total:');
                $(api.column(7).footer()).html(totalTimeWorked);
                $(api.column(8).footer()).html(totalTimeRemaining);
            }
        });
        /* End Datatables */

    });

    function padTo2Digits(num) {
        return num.toString().padStart(2, '0');
    }

    function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
    }

    function resetTimeControl() {
        $("#monthDropdown").each(function () {
            this.selectedIndex = 0
        });
        $("#yearDropdown").each(function () {
            this.selectedIndex = 0
        });

        var user = getUrlParameter('user');
        var userParam = '';

        if (user) {
            userParam = '?user=' + user;
        }

        dTable.ajax.url('/signing/personal/time-control/dt' + userParam).load();
    }

    function updateTimeControl() {

        var month = $('#monthDropdown').val();
        var year = $('#yearDropdown').val();

        if (!month || !year) {
            return;
        }

        var user = getUrlParameter('user');
        var userParam = '';

        if (user) {
            userParam = 'user=' + user + '&';
        }

        dTable.ajax.url('/signing/personal/time-control/dt?' + userParam + 'month=' + month + '&year=' + year).load();
    }

</script>