<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach var="node" items="${orgTree}" varStatus="vs">
	<c:choose>
		<c:when test="${orgLevel == 0}">
			<ul id="orgTree" class="treeview">
				<c:choose>
					<c:when test="${fn:length(node.children.list) > 0}">
						<li class="collapsable"><div
								class="hitarea expandable-hitarea"></div> <span
							type="${node.type }" nodeId="${node.id }"
							nodeText="${node.text }" nodePId="${node.parentId }" class=""
							style="font-weight: bolder;">
							<c:if test="${node.type eq 1 }">
							<input name="postName"
								value="${node.id}" type="radio" />
							</c:if> ${node.text }</span> <c:set
								var="orgLevel" value="${orgLevel + 1}" scope="request" /> <c:set
								var="orgTree" value="${node.children.list}" scope="request" />
							<ul style="display: none;">
								<c:import url="__org.jsp" />
							</ul></li>
					</c:when>
					<c:otherwise>
						<li><span type="${node.type }" nodeId="${node.id }"
							nodeText="${node.text }" nodePId="${node.parentId }" class="">
							<c:if test="${node.type eq 1 }">
							<input
								name="postName" value="${node.id}" type="radio" />
							</c:if>${node.text }</span></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${fn:length(node.children.list) > 0}">
					<li class="expandable"><div class="hitarea expandable-hitarea"></div>
						<span type="${node.type }" nodeId="${node.id }"
						nodeText="${node.text }" nodePId="${node.parentId }" class="">
						<c:if test="${node.type eq 1 }"><input
							name="postName" value="${node.id}"  type="radio" />
						</c:if>${node.text }</span> <c:set
							var="orgLevel" value="${orgLevel + 1}" scope="request" /> <c:set
							var="orgTree" value="${node.children.list}" scope="request" />
						<ul style="display: none;">
							<c:import url="__org.jsp" />
						</ul></li>
				</c:when>
				<c:otherwise>
					<li><span type="${node.type }" nodeId="${node.id }"
						nodeText="${node.text }" nodePId="${node.parentId }" class="">
						<c:if test="${node.type eq 1 }"><input
							name="postName" value="${node.id}"  type="radio" />
						</c:if>${node.text }</span></li>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<c:set var="orgIndex" value="${orgIndex + 1}" scope="request" />
</c:forEach>
<c:set var="orgLevel" value="${orgLevel - 1}" scope="request" />

